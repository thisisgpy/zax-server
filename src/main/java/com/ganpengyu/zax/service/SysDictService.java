package com.ganpengyu.zax.service;

import com.ganpengyu.zax.beanmapper.DictBeanMapper;
import com.ganpengyu.zax.beanmapper.DictItemBeanMapper;
import com.ganpengyu.zax.common.UserContext;
import com.ganpengyu.zax.common.page.Page;
import com.ganpengyu.zax.common.page.Paging;
import com.ganpengyu.zax.common.util.CheckUtils;
import com.ganpengyu.zax.dao.SysDictDao;
import com.ganpengyu.zax.dao.SysDictItemDao;
import com.ganpengyu.zax.model.SysDict;
import com.ganpengyu.zax.model.SysDictItem;
import com.ganpengyu.zax.service.dto.SysDictItemTree;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据字典服务
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/17
 */
@Service
public class SysDictService {

    @Resource
    private SysDictDao sysDictDao;

    @Resource
    private SysDictItemDao sysDictItemDao;

    @Resource
    private DictBeanMapper dictBeanMapper;

    @Resource
    private DictItemBeanMapper dictItemBeanMapper;

    /**
     * 创建数据字典
     *
     * @param sysDict {@link SysDict} 数据字典对象
     * @return {@link SysDict} 数据字典详情
     */
    @Transactional
    public SysDict createDict(SysDict sysDict) {
        sysDict.setCreateTime(LocalDateTime.now());
        sysDict.setCreateBy(UserContext.getUsername());
        int rows = sysDictDao.insert(sysDict);
        CheckUtils.check(rows > 0, "保存数据字典失败。");
        return sysDict;
    }

    /**
     * 更新数据字典
     *
     * @param sysDict {@link SysDict} 数据字典对象
     * @return {@link Boolean} 是否成功
     */
    @Transactional
    public boolean updateDict(SysDict sysDict) {
        SysDict currentDict = getDict(sysDict.getId());
        CheckUtils.check(currentDict != null, "数据字典不存在。ID:" + sysDict.getId());
        dictBeanMapper.map(sysDict, currentDict);
        currentDict.setUpdateTime(LocalDateTime.now());
        currentDict.setUpdateBy(UserContext.getUsername());
        int rows = sysDictDao.updateSelective(currentDict);
        CheckUtils.check(rows > 0, "更新数据字典失败。");
        return true;
    }

    /**
     * 分页查询数据字典
     *
     * @param pageNo   {@link Long} 页码
     * @param pageSize {@link Integer} 每页条数
     * @param sysDict  {@link SysDict} 数据字典查询条件
     * @return {@link Page<SysDict>} 数据字典分页列表
     */
    public Page<SysDict> listDict(long pageNo, int pageSize, SysDict sysDict) {
        Paging paging = new Paging(pageNo, pageSize);
        List<SysDict> rows = sysDictDao.list(sysDict, paging);
        return new Page<>(paging, rows);
    }

    /**
     * 删除数据字典
     *
     * @param id {@link Integer} 数据字典ID
     * @return {@link Boolean} 是否成功
     */
    @Transactional
    public boolean deleteDict(Integer id) {
        SysDict currentDict = getDict(id);
        CheckUtils.check(currentDict != null, "数据字典不存在。ID:" + id);
        List<SysDictItem> dictItems = getItemsByDictId(id);
        CheckUtils.check(dictItems.isEmpty(), "数据字典存在字典项，不能删除。");
        int rows = sysDictDao.delete(id);
        CheckUtils.check(rows > 0, "删除数据字典失败。");
        return true;
    }

    /**
     * 获取数据字典详情
     *
     * @param id {@link Integer} 数据字典ID
     * @return {@link SysDict} 数据字典详情
     */
    public SysDict getDict(Integer id) {
        SysDict sysDict = sysDictDao.selectById(id);
        CheckUtils.check(sysDict != null, "数据字典不存在。ID:" + id);
        return sysDict;
    }

    /**
     * 获取数据字典详情
     *
     * @param code {@link String} 数据字典编码
     * @return {@link SysDict} 数据字典详情
     */
    public SysDict getDictByCode(String code) {
        SysDict sysDict = sysDictDao.selectByCode(code);
        CheckUtils.check(sysDict != null, "数据字典不存在。Code:" + code);
        return sysDict;
    }

    /**
     * 创建数据字典项
     *
     * @param sysDictItem {@link SysDictItem} 数据字典项
     * @return {@link SysDictItem} 数据字典项详情
     */
    @Transactional
    public SysDictItem createDictItem(SysDictItem sysDictItem) {
        sysDictItem.setCreateTime(LocalDateTime.now());
        sysDictItem.setCreateBy(UserContext.getUsername());
        int rows = sysDictItemDao.insert(sysDictItem);
        CheckUtils.check(rows > 0, "保存数据字典项失败。");
        return sysDictItem;
    }

    /**
     * 更新数据字典项
     *
     * @param sysDictItem {@link SysDictItem} 数据字典项
     * @return {@link Boolean} 是否成功
     */
    @Transactional
    public boolean updateDictItem(SysDictItem sysDictItem) {
        SysDictItem currentDictItem = getDictItem(sysDictItem.getId());
        CheckUtils.check(currentDictItem != null, "数据字典项不存在。ID:" + sysDictItem.getId());
        dictItemBeanMapper.map(sysDictItem, currentDictItem);
        currentDictItem.setUpdateTime(LocalDateTime.now());
        currentDictItem.setUpdateBy(UserContext.getUsername());
        int rows = sysDictItemDao.updateSelective(currentDictItem);
        CheckUtils.check(rows > 0, "更新数据字典项失败。");
        return true;
    }

    /**
     * 获取数据字典项列表
     *
     * @param dictId {@link Integer} 数据字典ID
     * @return {@link List<SysDictItem>} 数据字典项列表
     */
    public List<SysDictItem> getItemsByDictId(Integer dictId) {
        return sysDictItemDao.selectByDictId(dictId);
    }

    /**
     * 获取数据字典项列表
     *
     * @param parentId {@link Integer} 父级ID
     * @return {@link List<SysDictItem>} 数据字典项列表
     */
    public List<SysDictItem> getItemsByParentId(Integer parentId) {
        return sysDictItemDao.selectByParentId(parentId);
    }

    /**
     * 获取数据字典项详情
     *
     * @param id {@link Integer} 数据字典项ID
     * @return {@link SysDictItem} 数据字典项详情
     */
    public SysDictItem getDictItem(Integer id) {
        SysDictItem sysDictItem = sysDictItemDao.selectById(id);
        CheckUtils.check(sysDictItem != null, "数据字典项不存在。ID:" + id);
        return sysDictItem;
    }

    /**
     * 获取数据字典项树
     *
     * @param dictId {@link Integer} 数据字典ID
     * @return {@link SysDictItemTree} 数据字典项树
     */
    public SysDictItemTree getDictItemTreeByDictId(Integer dictId) {
        SysDictItemTree root = new SysDictItemTree();
        List<SysDictItemTree> children = getItemChildren(dictId, 0);
        root.setChildren(children);
        return root;
    }

    /**
     * 获取数据字典项树
     *
     * @param dictCode {@link String} 数据字典编码
     * @return {@link SysDictItemTree} 数据字典项树
     */
    public SysDictItemTree getDictItemTreeByDictCode(String dictCode) {
        SysDict dict = getDictByCode(dictCode);
        SysDictItemTree root = new SysDictItemTree();
        List<SysDictItemTree> children = getItemChildren(dict.getId(), 0);
        root.setChildren(children);
        return root;
    }

    private List<SysDictItemTree> getItemChildren(Integer dictId, Integer itemParentId) {
        List<SysDictItem> subItems = sysDictItemDao.selectByDictIdAndItemParentId(dictId, itemParentId);
        List<SysDictItemTree> children = dictItemBeanMapper.toSysDictItemTree(subItems);
        for (SysDictItemTree child : children) {
            List<SysDictItemTree> c = getItemChildren(dictId, child.getId());
            child.setChildren(c);
        }
        return children;
    }

    /**
     * 删除数据字典项
     *
     * @param id {@link Integer} 数据字典项ID
     * @return {@link Boolean} 是否成功
     */
    @Transactional
    public boolean deleteDictItem(Integer id) {
        SysDictItem currentDictItem = getDictItem(id);
        CheckUtils.check(currentDictItem != null, "数据字典项不存在。ID:" + id);
        int rows = sysDictItemDao.delete(id);
        CheckUtils.check(rows > 0, "删除数据字典项失败。");
        return true;
    }


}
