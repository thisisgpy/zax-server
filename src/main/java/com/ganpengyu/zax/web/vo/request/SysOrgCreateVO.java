package com.ganpengyu.zax.web.vo.request;

import lombok.Data;

/**
 * 创建组织
 * @author Pengyu Gan
 * CreateDate 2025/3/12
 */
@Data
public class SysOrgCreateVO {

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织名称简称
     */
    private String nameAbbr;

    /**
     * 组织备注
     */
    private String comment;

    /**
     * 父级组织ID. 0表示没有父组织
     */
    private Long parentId;

}
