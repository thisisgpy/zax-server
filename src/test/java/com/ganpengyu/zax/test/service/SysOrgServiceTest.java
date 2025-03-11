package com.ganpengyu.zax.test.service;

import com.ganpengyu.zax.model.SysOrg;
import com.ganpengyu.zax.service.SysOrgService;
import com.ganpengyu.zax.service.dto.SysOrgTree;
import com.ganpengyu.zax.test.ZaxApplicationTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
public class SysOrgServiceTest extends ZaxApplicationTest {

    @Resource
    private SysOrgService sysOrgService;

    @Test
    public void testGenerateOrgCode() {
        String code = sysOrgService.generateOrgCode(685148377310040064L);
        Assertions.assertEquals("00020004000200010001", code);
    }

    @Test
    public void testUpdateOrg() {
        SysOrg org = new SysOrg();
        org.setId(685012204365287424L);
        org.setParentId(0L);
//        org.setName("成都天府粮仓农旅投资发展集团有限公司");
//        org.setNameAbbr("天府粮仓");
//        org.setComment("以老旅投为核心");

        boolean success = sysOrgService.updateOrg(org);
        Assertions.assertTrue(success);
    }

    @Test
    public void testGetOrgTrees() {
        List<SysOrgTree> trees = sysOrgService.getOrgTrees(685010161999286272L);
        Assertions.assertEquals(1, trees.size());
    }

}
