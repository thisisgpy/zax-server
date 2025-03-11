package com.ganpengyu.zax.web;

import com.ganpengyu.zax.service.SysOrgService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
