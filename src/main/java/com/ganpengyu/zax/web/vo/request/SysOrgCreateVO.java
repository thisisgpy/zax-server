package com.ganpengyu.zax.web.vo.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 创建组织
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/12
 */
@Data
public class SysOrgCreateVO {

    /**
     * 组织名称
     */
    @NotEmpty(message = "组织名称不能为空")
    @Length(max = 64, message = "组织名称长度不能超过64个字符")
    private String name;

    /**
     * 组织名称简称
     */
    @NotEmpty(message = "组织名称简称不能为空")
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
