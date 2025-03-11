package com.ganpengyu.zax.service;

import com.ganpengyu.zax.beanmapper.OrgBeanMapper;
import com.ganpengyu.zax.common.id.SnowflakeIDGenerator;
import com.ganpengyu.zax.common.util.CheckUtils;
import com.ganpengyu.zax.dao.SysOrgDao;
import com.ganpengyu.zax.model.SysOrg;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织服务
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
@Slf4j
@Service
public class SysOrgService {

    @Resource
    private SysOrgDao sysOrgDao;

    @Resource
    private SnowflakeIDGenerator idGenerator;

    @Resource
    private OrgBeanMapper orgBeanMapper;

    @Transactional
    public SysOrg createOrg(SysOrg org) {
        String orgCode = this.generateOrgCode(org.getParentId());
        long orgId = idGenerator.getId();
        org.setId(orgId);
        org.setCode(orgCode);
        org.setCreateTime(LocalDateTime.now());
        org.setCreateBy("admin");
        int rows = sysOrgDao.insert(org);
        CheckUtils.check(rows != 0, "保存组织失败");
        return org;
    }

    @Transactional
    public SysOrg updateOrg(SysOrg org) {
        SysOrg currentOrg = getOrg(org.getId());
        List<SysOrg> preUpdateOrgs = new ArrayList<>();
        // 未变更父组织，直接更新
        if (org.getParentId().equals(currentOrg.getParentId())) {
            orgBeanMapper.map(org, currentOrg);
            preUpdateOrgs.add(currentOrg);
            log.info("[更新组织]组织 {} 的父组织未发生变更", org.getId());
        } else {
            // 不是变更为顶级组织，校验变更后的层级关系
            if (org.getParentId() != 0) {
                SysOrg parentOrg = getOrg(org.getParentId());
                CheckUtils.check(!parentOrg.getId().equals(org.getId()), "父组织不能是自己");
                String parentOrgCode = parentOrg.getCode();
                String currentOrgCode = currentOrg.getCode();
                log.info("[更新组织]不是变更为顶级组织. 当前组织Code {}, 目标父组织Code {}", currentOrgCode, parentOrgCode);
                // 父组织编码的长度不能大于当前组织编码的长度
                boolean isParentCodeShorterThanCurrent = parentOrgCode.length() <= currentOrgCode.length();
                // 父组织编码不能以当前组织编码开头
                boolean isParentCodeNotStartWithCurrent = !parentOrgCode.startsWith(currentOrgCode);
                CheckUtils.check(isParentCodeShorterThanCurrent && isParentCodeNotStartWithCurrent, "不允许使用自己的子组织作为父组织");
            }
            // 根据新的父组织，生成当前组织的新编码
            String newOrgCode = this.generateOrgCode(org.getParentId());
            log.info("[更新组织]组织 {} 的当前 Code {}, 新 Code {}", org.getId(), currentOrg.getCode(), newOrgCode);
            // 重新计算当前组织下所有子孙组织的编码
            List<SysOrg> preUpdateDescendants = this.reCalculateDescendantCode(currentOrg.getCode(), newOrgCode);
            log.info("[更新组织]组织 {} 的 Code 变更为 {}, 受影响的子孙组织有 {} 个", org.getId(), newOrgCode, preUpdateDescendants.size());
            preUpdateOrgs.addAll(preUpdateDescendants);
            // 更新组织信息
            org.setCode(newOrgCode);
            org.setUpdateTime(LocalDateTime.now());
            org.setUpdateBy("admin");
            orgBeanMapper.map(org, currentOrg);
            preUpdateOrgs.add(currentOrg);
        }
        for (SysOrg preUpdateOrg : preUpdateOrgs) {
            int rows = sysOrgDao.updateSelective(preUpdateOrg);
            CheckUtils.check(rows != 0, "更新组织信息失败。组织ID:" + preUpdateOrg.getId());
        }
        return currentOrg;
    }

    private List<SysOrg> reCalculateDescendantCode(String oldOrgCode, String newOrgCode) {
        List<SysOrg> preUpdateDescendants = new ArrayList<>();
        List<SysOrg> descendants = sysOrgDao.selectDescendants(oldOrgCode);
        if (!descendants.isEmpty()) {
            descendants.forEach(descendant -> {
                String newCode = descendant.getCode().replace(oldOrgCode, newOrgCode);
                descendant.setName(newCode);
                descendant.setUpdateTime(LocalDateTime.now());
                descendant.setUpdateBy("admin");
                preUpdateDescendants.add(descendant);
            });
        }
        return preUpdateDescendants;
    }

    public String generateOrgCode(Long parentId) {
        CheckUtils.check(parentId != null, "无法生成组织编码");
        String parentOrgCode = "";
        if (parentId != 0) {
            SysOrg parentOrg = getOrg(parentId);
            parentOrgCode = parentOrg.getCode();
        }
        String maxCode = sysOrgDao.selectMaxCode(parentId);
        if (maxCode == null) {
            return String.format("%s0001", parentOrgCode);
        }
        int maxCodeSerial = Integer.parseInt(maxCode.substring(maxCode.length() - 4));
        int nextCodeSerial = maxCodeSerial + 1;
        return String.format("%s%04d", parentOrgCode, nextCodeSerial);
    }

    public SysOrg getOrg(Long id) {
        SysOrg org = sysOrgDao.selectById(id);
        CheckUtils.check(org != null, "组织不存在。ID:" + id);
        return org;
    }

}
