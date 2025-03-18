package com.ganpengyu.zax.dao;

import com.ganpengyu.zax.common.page.Paging;
import com.ganpengyu.zax.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/17
 */
public interface SysDictDao {

    /**
     * 新增字典
     *
     * @param sysDict {@link SysDict} 字典信息
     * @return 影响行数
     */
    int insert(SysDict sysDict);

    /**
     * 更新字典
     *
     * @param sysDict {@link SysDict} 字典信息
     * @return 影响行数
     */
    int updateSelective(SysDict sysDict);

    /**
     * 查找字典
     *
     * @param id 字典 ID
     * @return {@link SysDict} 字典信息
     */
    SysDict selectById(Integer id);

    /**
     * 查找字典
     *
     * @param code 字典编码
     * @return {@link SysDict} 字典信息
     */
    SysDict selectByCode(String code);

    /**
     * 分页查询字典
     *
     * @param sysDict {@link SysDict} 字典信息
     * @return 字典列表
     */
    List<SysDict> list(@Param("condition") SysDict sysDict, Paging paging);

    /**
     * 删除字典
     *
     * @param id 字典 ID
     * @return 影响行数
     */
    int delete(Integer id);
}