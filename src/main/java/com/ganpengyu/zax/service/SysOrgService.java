package com.ganpengyu.zax.service;

import com.ganpengyu.zax.beanmapper.OrgBeanMapper;
import com.ganpengyu.zax.common.UserContext;
import com.ganpengyu.zax.common.id.SnowflakeIDGenerator;
import com.ganpengyu.zax.common.util.CheckUtils;
import com.ganpengyu.zax.dao.SysOrgDao;
import com.ganpengyu.zax.model.SysOrg;
import com.ganpengyu.zax.service.dto.SysOrgTree;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * 创建组织
     * @param org {@link SysOrg} 组织信息
     * @return {@link SysOrg} 组织信息
     */
    @Transactional
    public SysOrg createOrg(SysOrg org) {
        String orgCode = this.generateOrgCode(org.getParentId());
        long orgId = idGenerator.getId();
        org.setId(orgId);
        org.setCode(orgCode);
        org.setCreateTime(LocalDateTime.now());
        org.setCreateBy(UserContext.getUsername());
        int rows = sysOrgDao.insert(org);
        CheckUtils.check(rows != 0, "保存组织失败");
        return org;
    }

    /**
     * 编辑组织信息。如果组织的父组织发生变动，会同步更新子孙组织的编码。
     * @param org {@link SysOrg} 组织信息
     * @return true 编辑成功
     */
    @Transactional
    public boolean updateOrg(SysOrg org) {
        SysOrg currentOrg = getOrg(org.getId());
        List<SysOrg> preUpdateOrgs = new ArrayList<>();
        // 未变更父组织，直接更新
        if (org.getParentId().equals(currentOrg.getParentId())) {
            orgBeanMapper.map(org, currentOrg);
            org.setUpdateTime(LocalDateTime.now());
            org.setUpdateBy(UserContext.getUsername());
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
                // 父组织编码不能以当前组织编码开头
                boolean isParentCodeNotStartWithCurrent = !parentOrgCode.startsWith(currentOrgCode);
                CheckUtils.check(isParentCodeNotStartWithCurrent, "不允许使用自己的子组织作为父组织");
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
            org.setUpdateBy(UserContext.getUsername());
            orgBeanMapper.map(org, currentOrg);
            preUpdateOrgs.add(currentOrg);
        }
        for (SysOrg preUpdateOrg : preUpdateOrgs) {
            int rows = sysOrgDao.updateSelective(preUpdateOrg);
            CheckUtils.check(rows != 0, "更新组织信息失败。组织ID:" + preUpdateOrg.getId());
        }
        return true;
    }

    /**
     * 删除组织
     * @param orgId 组织 ID
     * @return true 删除成功
     */
    @Transactional
    public boolean removeOrg(Long orgId) {
        getOrg(orgId);
        List<SysOrg> children = getChildren(orgId);
        CheckUtils.check(children.isEmpty(), "存在子组织，无法删除。");
        int rows = sysOrgDao.delete(Collections.singletonList(orgId));
        CheckUtils.check(rows != 0, "组织删除失败。组织ID:" + orgId);
        return true;
    }

    /**
     * 查找组织
     * @param id 组织 ID
     * @return {@link SysOrg} 组织信息
     */
    public SysOrg getOrg(Long id) {
        SysOrg org = sysOrgDao.selectById(id);
        CheckUtils.check(org != null, "组织不存在。ID:" + id);
        return org;
    }

    /**
     * 查找指定组织的子组织
     * @param id 组织 ID
     * @return {@link SysOrg} 子组织集合
     */
    public List<SysOrg> getChildren(Long id) {
        return sysOrgDao.selectByParentId(id);
    }

    /**
     * 查找指定组织的子孙组织
     * @param orgId 组织 ID
     * @return {@link SysOrgTree} 组织树
     */
    public List<SysOrgTree> getDescendants(Long orgId) {
        List<SysOrg> children = getChildren(orgId);
        List<SysOrgTree> descendants = orgBeanMapper.toSysOrgTree(children);
        for (SysOrgTree descendant : descendants) {
            List<SysOrgTree> c = getDescendants(descendant.getId());
            descendant.setChildren(c);
        }
        return descendants;
    }

    /**
     * 查找组织树。如果传入 0，表示查询所有的组织树。
     * @param rootOrgId 根组织 ID
     * @return {@link SysOrgTree} 组织树集合
     */
    public List<SysOrgTree> getOrgTrees(Long rootOrgId) {
        List<SysOrg> rootOrgs = new ArrayList<>();
        if (rootOrgId == 0) {
            List<SysOrg> orgs = this.getChildren(0L);
            rootOrgs.addAll(orgs);
        } else {
            SysOrg rootOrg = this.getOrg(rootOrgId);
            CheckUtils.check(rootOrg.getParentId().equals(0L), "只允许从根组织开始查询组织树.组织ID:" + rootOrgId);
            rootOrgs.add(rootOrg);
        }
        List<SysOrgTree> descendants = orgBeanMapper.toSysOrgTree(rootOrgs);
        for (SysOrgTree descendant : descendants) {
            List<SysOrgTree> c = getDescendants(descendant.getId());
            descendant.setChildren(c);
        }
        return descendants;
    }

    /**
     * 查找指定组织所在的组织树
     * @param orgId 组织 ID
     * @return {@link SysOrgTree} 组织树
     */
    public SysOrgTree locateOrgTree(Long orgId) {
        SysOrg currentOrg = getOrg(orgId);
        String currentRootOrgCode = currentOrg.getCode().substring(0, 4);
        SysOrg rootOrg = sysOrgDao.selectByCode(currentRootOrgCode);
        List<SysOrgTree> trees = this.getOrgTrees(rootOrg.getId());
        return trees.getFirst();
    }

    /**
     * 计算子孙组织的新编码
     * @param oldOrgCode 父组织旧编码
     * @param newOrgCode 父组织新编码
     * @return {@link SysOrg} 子孙组织集合
     */
    private List<SysOrg> reCalculateDescendantCode(String oldOrgCode, String newOrgCode) {
        List<SysOrg> preUpdateDescendants = new ArrayList<>();
        List<SysOrg> descendants = sysOrgDao.selectDescendants(oldOrgCode);
        if (!descendants.isEmpty()) {
            descendants.forEach(descendant -> {
                String newCode = descendant.getCode().replace(oldOrgCode, newOrgCode);
                descendant.setCode(newCode);
                descendant.setUpdateTime(LocalDateTime.now());
                descendant.setUpdateBy("admin");
                preUpdateDescendants.add(descendant);
            });
        }
        return preUpdateDescendants;
    }

    /**
     * 生成组织编码
     * @param parentId 父组织 ID
     * @return 组织编码
     */
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

}
