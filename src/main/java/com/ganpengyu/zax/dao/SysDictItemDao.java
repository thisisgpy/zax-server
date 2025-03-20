package com.ganpengyu.zax.dao;

import com.ganpengyu.zax.model.SysDictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典项
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/17
 */
public interface SysDictItemDao {

    /**
     * 新增字典项
     *
     * @param sysDictItem {@link SysDictItem} 字典项信息
     * @return 影响行数
     */
    int insert(SysDictItem sysDictItem);

    /**
     * 更新字典项
     *
     * @param sysDictItem {@link SysDictItem} 字典项信息
     * @return 影响行数
     */
    int updateSelective(SysDictItem sysDictItem);

    /**
     * 查找字典项
     *
     * @param id 字典项 ID
     * @return {@link SysDictItem} 字典项信息
     */
    SysDictItem selectById(Integer id);

    /**
     * 根据父 ID 查找字典项
     *
     * @param parentId 父 ID
     * @return 字典项列表
     */
    List<SysDictItem> selectByParentId(Integer parentId);

    /**
     * 根据字典 ID 查找字典项
     *
     * @param dictId 字典 ID
     * @return 字典项列表
     */
    List<SysDictItem> selectByDictId(Integer dictId);

    /**
     * 根据字典 ID 和字典项 parentId 查询字典项
     *
     * @param dictId   字典 ID
     * @param parentId 字典项 parentId
     * @return 字典项列表
     */
    List<SysDictItem> selectByDictIdAndItemParentId(@Param("dictId") Integer dictId, @Param("parentId") Integer parentId);

    /**
     * 根据字典代码查找字典项
     *
     * @param dictCode 字典代码
     * @return 字典项列表
     */
    List<SysDictItem> selectByDictCode(String dictCode);

    /**
     * 删除字典项
     *
     * @param id 字典项 ID
     * @return 影响行数
     */
    int delete(Integer id);
}