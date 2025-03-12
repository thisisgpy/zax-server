package com.ganpengyu.zax.web;

import com.ganpengyu.zax.beanmapper.OrgBeanMapper;
import com.ganpengyu.zax.common.ZaxResult;
import com.ganpengyu.zax.model.SysOrg;
import com.ganpengyu.zax.service.SysOrgService;
import com.ganpengyu.zax.service.dto.SysOrgTree;
import com.ganpengyu.zax.web.vo.request.SysOrgCreateVO;
import com.ganpengyu.zax.web.vo.request.SysOrgUpdateVO;
import com.ganpengyu.zax.web.vo.response.SysOrgVO;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织接口
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
@RestController
@RequestMapping(value = "/api/v1/org")
public class SysOrgController {

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private OrgBeanMapper orgBeanMapper;

    @PostMapping(value = "/create")
    public ZaxResult<SysOrg> createOrg(@RequestBody SysOrgCreateVO sysOrgCreateVO) {
        SysOrg sysOrg = orgBeanMapper.toSysOrg(sysOrgCreateVO);
        SysOrg org = sysOrgService.createOrg(sysOrg);
        return ZaxResult.ok(org);
    }

    @PostMapping(value = "/edit")
    public ZaxResult<Boolean> editOrg(@RequestBody SysOrgUpdateVO sysOrgUpdateVO) {
        SysOrg sysOrg = orgBeanMapper.toSysOrg(sysOrgUpdateVO);
        boolean success = sysOrgService.updateOrg(sysOrg);
        return ZaxResult.ok(success);
    }

    @GetMapping(value = "/remove/{orgId}")
    public ZaxResult<Boolean> removeOrg(@PathVariable("orgId") Long orgId) {
        boolean success = sysOrgService.removeOrg(orgId);
        return ZaxResult.ok(success);
    }

    @GetMapping(value = "/get/{orgId}")
    public ZaxResult<SysOrgVO> getOrg(@PathVariable("orgId") Long orgId) {
        SysOrg sysOrg = sysOrgService.getOrg(orgId);
        SysOrgVO sysOrgVO = orgBeanMapper.toSysOrgVO(sysOrg);
        return ZaxResult.ok(sysOrgVO);
    }

    @GetMapping(value = "/children/{orgId}")
    public ZaxResult<List<SysOrgVO>> getChildren(@PathVariable("orgId") Long orgId) {
        List<SysOrg> children = sysOrgService.getChildren(orgId);
        List<SysOrgVO> sysOrgVOList = orgBeanMapper.toSysOrgVOList(children);
        return ZaxResult.ok(sysOrgVOList);
    }

    @GetMapping(value = "/descendants/{orgId}")
    public ZaxResult<List<SysOrgTree>> getDescendants(@PathVariable("orgId") Long orgId) {
        List<SysOrgTree> descendants = sysOrgService.getDescendants(orgId);
        return ZaxResult.ok(descendants);
    }

    @GetMapping(value = "/trees/{rootOrgId}")
    public ZaxResult<List<SysOrgTree>> getOrgTrees(@PathVariable(name = "rootOrgId", required = false) Long rootOrgId) {
        if (rootOrgId != null) {
            rootOrgId = 0L;
        }
        List<SysOrgTree> orgTrees = sysOrgService.getOrgTrees(rootOrgId);
        return ZaxResult.ok(orgTrees);
    }

    @GetMapping(value = "/locate/{orgId}")
    public ZaxResult<SysOrgTree> locateOrgTree(@PathVariable("orgId") Long orgId) {
        SysOrgTree sysOrgTree = sysOrgService.locateOrgTree(orgId);
        return ZaxResult.ok(sysOrgTree);
    }
}
