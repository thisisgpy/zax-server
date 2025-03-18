package com.ganpengyu.zax.web.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 编辑字典项
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/18
 */
@Data
public class SysDictItemUpdateVO {

    /**
     * 字典项ID
     */
    @NotNull(message = "字典项ID不能为空")
    private Integer id;

    /**
     * 字典项标签
     */
    @Length(max = 64, message = "字典项标签长度不能超过64个字符")
    private String label;

    /**
     * 字典项值
     */
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

    /**
     * 是否启用. 0: 禁用, 1: 启用
     */
    private Boolean enabled;

}
