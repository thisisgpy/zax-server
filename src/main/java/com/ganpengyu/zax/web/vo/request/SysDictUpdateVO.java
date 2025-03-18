package com.ganpengyu.zax.web.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 编辑字典
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/18
 */
@Data
public class SysDictUpdateVO {

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    private Integer id;

    /**
     * 字典名称
     */
    @Length(max = 64, message = "字典名称长度不能超过64个字符")
    private String name;

    /**
     * 字典备注
     */
    @Length(max = 128, message = "字典备注长度不能超过128个字符")
    private String comment;

    /**
     * 是否启用. 0: 禁用, 1: 启用
     */
    private Boolean enabled;

}
