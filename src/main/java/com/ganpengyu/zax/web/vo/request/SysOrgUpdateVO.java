package com.ganpengyu.zax.web.vo.request;

import lombok.Data;

/**
 * 编辑组织
 * @author Pengyu Gan
 * CreateDate 2025/3/12
 */
@Data
public class SysOrgUpdateVO {

    /**
     * 组织ID
     */
    private Long id;

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
