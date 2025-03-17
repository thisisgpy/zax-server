package com.ganpengyu.zax.service;

import com.ganpengyu.zax.beanmapper.DictBeanMapper;
import com.ganpengyu.zax.common.UserContext;
import com.ganpengyu.zax.common.page.Page;
import com.ganpengyu.zax.common.page.Paging;
import com.ganpengyu.zax.common.util.CheckUtils;
import com.ganpengyu.zax.dao.SysDictDao;
import com.ganpengyu.zax.model.SysDict;
import com.ganpengyu.zax.model.SysDictItem;
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
    private DictBeanMapper dictBeanMapper;

    @Resource
    private SysDictItemService sysDictItemService;

    @Transactional
    public SysDict createDict(SysDict sysDict) {
        sysDict.setCreateTime(LocalDateTime.now());
        sysDict.setCreateBy(UserContext.getUsername());
        int rows = sysDictDao.insert(sysDict);
        CheckUtils.check(rows > 0, "保存数据字典失败。");
        return sysDict;
    }

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

    public Page<SysDict> list(long pageNo, int pageSize, SysDict sysDict) {
        Paging paging = new Paging(pageNo, pageSize);
        List<SysDict> rows = sysDictDao.list(sysDict, paging);
        return new Page<>(paging, rows);
    }

    @Transactional
    public boolean deleteDict(Integer id) {
        SysDict currentDict = getDict(id);
        CheckUtils.check(currentDict != null, "数据字典不存在。ID:" + id);
        List<SysDictItem> dictItems = sysDictItemService.selectByDictId(id);
        CheckUtils.check(dictItems.isEmpty(), "数据字典存在字典项，不能删除。");
        int rows = sysDictDao.delete(id);
        CheckUtils.check(rows > 0, "删除数据字典失败。");
        return true;
    }

    public SysDict getDict(Integer id) {
        SysDict sysDict = sysDictDao.selectById(id);
        CheckUtils.check(sysDict != null, "数据字典不存在。ID:" + id);
        return sysDict;
    }

}
