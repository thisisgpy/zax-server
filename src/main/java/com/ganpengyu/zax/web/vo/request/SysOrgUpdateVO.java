package com.ganpengyu.zax.web.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @NotNull(message = "组织ID不能为空")
    private Long id;

    /**
     * 组织名称
     */
    @Length(max = 64, message = "组织名称长度不能超过64个字符")
    private String name;

    /**
     * 组织名称简称
     */
    @Length(max = 64, message = "组织名称简称长度不能超过64个字符")
    private String nameAbbr;

    /**
     * 组织备注
     */
    @Length(max = 128, message = "组织备注长度不能超过128个字符")
    private String comment;

    /**
     * 父级组织ID. 0表示没有父组织
     */
    private Long parentId;

}
