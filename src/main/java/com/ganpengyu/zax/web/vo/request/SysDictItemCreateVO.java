package com.ganpengyu.zax.web.vo.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 创建字典项
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/18
 */
@Data
public class SysDictItemCreateVO {

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    private Integer dictId;

    /**
     * 字典编码
     */
    @NotEmpty(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典项标签
     */
    @NotEmpty(message = "字典项标签不能为空")
    @Length(max = 64, message = "字典项标签长度不能超过64个字符")
    private String label;

    /**
     * 字典项值
     */
    @NotEmpty(message = "字典项值不能为空")
    @Length(max = 64, message = "字典项值长度不能超过64个字符")
    private String value;

    /**
     * 字典项备注
     */
    @Length(max = 128, message = "字典项备注长度不能超过128个字符")
    private String comment;

    /**
     * 字典项排序
     */
    private Integer sort;

    /**
     * 父级字典项ID. 0表示没有父级字典项
     */
    private Integer parentId;

}
