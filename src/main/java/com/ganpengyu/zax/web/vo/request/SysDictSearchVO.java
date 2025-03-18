package com.ganpengyu.zax.web.vo.request;

import lombok.Data;

/**
 * 字典搜索
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/18
 */
@Data
public class SysDictSearchVO {

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 是否启用. 0: 禁用, 1: 启用
     */
    private Boolean enabled;

}
