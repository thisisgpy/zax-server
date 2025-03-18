package com.ganpengyu.zax.web.vo.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 创建字典
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/18
 */
@Data
public class SysDictCreateVO {

    /**
     * 字典编码
     */
    @NotEmpty(message = "字典编码不能为空")
    @Length(max = 64, message = "字典编码长度不能超过64个字符")
    private String code;

    /**
     * 字典名称
     */
    @NotEmpty(message = "字典名称不能为空")
    @Length(max = 64, message = "字典名称长度不能超过64个字符")
    private String name;

    /**
     * 字典备注
     */
    private String comment;

}
