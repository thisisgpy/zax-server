package com.ganpengyu.zax.dao;

import com.ganpengyu.zax.model.SysOrg;

import java.util.List;

/**
 * 组织
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
public interface SysOrgDao {

    /**
     * 新增组织
     * @param sysOrg {@link SysOrg} 组织信息
     * @return 影响行数
     */
    int insert(SysOrg sysOrg);

    /**
     * 更新组织信息
     * @param sysOrg {@link SysOrg} 组织信息
     * @return 影响行数
     */
    int updateSelective(SysOrg sysOrg);

    /**
     * 删除组织
     * @param orgIds 组织 ID 列表
     * @return 影响行数
     */
    int delete(List<Long> orgIds);

    /**
     * 查询指定组织
     * @param id 组织 ID
     * @return {@link SysOrg} 组织信息
     */
    SysOrg selectById(Long id);

    /**
     * 查询指定父组织的子组织
     * @param parentId 父组织 ID
     * @return {@link SysOrg} 子组织集合
     */
    List<SysOrg> selectByParentId(Long parentId);

    /**
     * 根据组织编码查询组织
     * @param code 组织编码
     * @return {@link SysOrg} 组织信息
     */
    SysOrg selectByCode(String code);

    /**
     * 根据组织编码查询其下当前已用最大编码
     * @param parentId 父组织 ID
     * @return 其下当前已用最大编码
     */
    String selectMaxCode(Long parentId);

    /**
     * 查询指定组织下的全部子孙组织
     * @param code 组织编码
     * @return {@link SysOrg} 子孙组织集合
     */
    List<SysOrg> selectDescendants(String code);
}