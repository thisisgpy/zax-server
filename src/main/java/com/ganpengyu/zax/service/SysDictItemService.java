package com.ganpengyu.zax.service;

import com.ganpengyu.zax.beanmapper.DictItemBeanMapper;
import com.ganpengyu.zax.common.UserContext;
import com.ganpengyu.zax.common.util.CheckUtils;
import com.ganpengyu.zax.dao.SysDictItemDao;
import com.ganpengyu.zax.model.SysDictItem;
import com.ganpengyu.zax.service.dto.SysDictItemTree;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据字典项服务
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/17
 */
@Service
public class SysDictItemService {

    @Resource
    private SysDictItemDao sysDictItemDao;

    @Resource
    private DictItemBeanMapper dictItemBeanMapper;

    @Transactional
    public SysDictItem createDictItem(SysDictItem sysDictItem) {
        sysDictItem.setCreateTime(LocalDateTime.now());
        sysDictItem.setCreateBy(UserContext.getUsername());
        int rows = sysDictItemDao.insert(sysDictItem);
        CheckUtils.check(rows > 0, "保存数据字典项失败。");
        return sysDictItem;
    }

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

    public List<SysDictItem> selectByDictId(Integer dictId) {
        return sysDictItemDao.selectByDictId(dictId);
    }

    public List<SysDictItem> selectByDictCode(String dictCode) {
        return sysDictItemDao.selectByDictCode(dictCode);
    }
    
    public List<SysDictItem> selectByParentId(Integer parentId) {
        return sysDictItemDao.selectByParentId(parentId);
    }

    public SysDictItem getDictItem(Integer id) {
        SysDictItem sysDictItem = sysDictItemDao.selectById(id);
        CheckUtils.check(sysDictItem != null, "数据字典项不存在。ID:" + id);
        return sysDictItem;
    }

    public SysDictItemTree getDictItemTree(Integer dictId) {
        SysDictItemTree root = new SysDictItemTree();
        List<SysDictItemTree> children = getChildren(dictId);
        root.setChildren(children);
        return root;
    }

    public List<SysDictItemTree> getChildren(Integer dictId) {
        List<SysDictItem> subItems = selectByParentId(dictId);
        List<SysDictItemTree> children = dictItemBeanMapper.toSysDictItemTree(subItems);
        for (SysDictItemTree child : children) {
            List<SysDictItemTree> c = getChildren(child.getId());
            child.setChildren(c);
        }
        return children;
    }

    @Transactional
    public boolean deleteDictItem(Integer id) {
        SysDictItem currentDictItem = getDictItem(id);
        CheckUtils.check(currentDictItem != null, "数据字典项不存在。ID:" + id);
        int rows = sysDictItemDao.delete(id);
        CheckUtils.check(rows > 0, "删除数据字典项失败。");
        return true;
    }

}
