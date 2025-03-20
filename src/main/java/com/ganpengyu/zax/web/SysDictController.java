package com.ganpengyu.zax.web;

import com.ganpengyu.zax.beanmapper.DictBeanMapper;
import com.ganpengyu.zax.beanmapper.DictItemBeanMapper;
import com.ganpengyu.zax.common.ZaxResult;
import com.ganpengyu.zax.common.page.Page;
import com.ganpengyu.zax.model.SysDict;
import com.ganpengyu.zax.model.SysDictItem;
import com.ganpengyu.zax.service.SysDictService;
import com.ganpengyu.zax.service.dto.SysDictItemTree;
import com.ganpengyu.zax.web.vo.request.*;
import com.ganpengyu.zax.web.vo.response.SysDictItemVO;
import com.ganpengyu.zax.web.vo.response.SysDictVO;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典接口
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/18
 */
@RestController
@RequestMapping(value = "/api/v1/dict")
public class SysDictController {

    @Resource
    private SysDictService sysDictService;

    @Resource
    private DictBeanMapper dictBeanMapper;

    @Resource
    private DictItemBeanMapper dictItemBeanMapper;

    @PostMapping("/create")
    public ZaxResult<SysDictVO> createDict(@RequestBody @Valid SysDictCreateVO sysDictCreateVO) {
        SysDict sysDict = dictBeanMapper.toSysDict(sysDictCreateVO);
        SysDict dict = sysDictService.createDict(sysDict);
        SysDictVO sysDictVO = dictBeanMapper.toSysDictVO(dict);
        return ZaxResult.ok(sysDictVO);
    }

    @PostMapping("/edit")
    public ZaxResult<Boolean> editDict(@RequestBody @Valid SysDictUpdateVO sysDictUpdateVO) {
        SysDict sysDict = dictBeanMapper.toSysDict(sysDictUpdateVO);
        boolean success = sysDictService.updateDict(sysDict);
        return ZaxResult.ok(success);
    }

    @GetMapping("/remove/{dictId}")
    public ZaxResult<Boolean> removeDict(@PathVariable("dictId") Integer id) {
        boolean success = sysDictService.deleteDict(id);
        return ZaxResult.ok(success);
    }

    @PostMapping(value = "/search/{pageNo}/{pageSize}")
    public ZaxResult<Page<SysDictVO>> listDict(@PathVariable("pageNo") Long pageNo,
                                               @PathVariable("pageSize") Integer pageSize,
                                               @RequestBody SysDictSearchVO sysDictSearchVO) {
        SysDict sysDict = dictBeanMapper.toSysDict(sysDictSearchVO);
        Page<SysDict> page = sysDictService.listDict(pageNo, pageSize, sysDict);
        List<SysDict> rows = page.getRows();
        List<SysDictVO> sysDictVOList = dictBeanMapper.toSysDictVOList(rows);
        Page<SysDictVO> result = new Page<>();
        result.setRows(sysDictVOList);
        result.setPageNo(page.getPageNo());
        result.setPageSize(page.getPageSize());
        result.setTotalCount(page.getTotalCount());
        result.setTotalPages(page.getTotalPages());
        return ZaxResult.ok(result);
    }

    @GetMapping(value = "/get/{dictId}")
    public ZaxResult<SysDictVO> getDict(@PathVariable("dictId") Integer dictId) {
        SysDict dict = sysDictService.getDict(dictId);
        SysDictVO sysDictVO = dictBeanMapper.toSysDictVO(dict);
        return ZaxResult.ok(sysDictVO);
    }

    @PostMapping("/item/create")
    public ZaxResult<SysDictItemVO> createDictItem(@RequestBody @Valid SysDictItemCreateVO sysDictItemCreateVO) {
        SysDictItem sysDictItem = dictItemBeanMapper.toSysDictItem(sysDictItemCreateVO);
        SysDictItem dictItem = sysDictService.createDictItem(sysDictItem);
        SysDictItemVO sysDictItemVO = dictItemBeanMapper.toSysDictItemVO(dictItem);
        return ZaxResult.ok(sysDictItemVO);
    }

    @PostMapping("/item/edit")
    public ZaxResult<Boolean> editDictItem(@RequestBody @Valid SysDictItemUpdateVO sysDictItemUpdateVO) {
        SysDictItem sysDictItem = dictItemBeanMapper.toSysDictItem(sysDictItemUpdateVO);
        boolean success = sysDictService.updateDictItem(sysDictItem);
        return ZaxResult.ok(success);
    }

    @GetMapping("/item/remove/{dictItemId}")
    public ZaxResult<Boolean> removeDictItem(@PathVariable("dictItemId") Integer dictItemId) {
        boolean success = sysDictService.deleteDictItem(dictItemId);
        return ZaxResult.ok(success);
    }

    @GetMapping(value = "/item/get/{itemId}")
    public ZaxResult<SysDictItemVO> getDictItem(@PathVariable("itemId") Integer itemId) {
        SysDictItem dictItem = sysDictService.getDictItem(itemId);
        SysDictItemVO sysDictItemVO = dictItemBeanMapper.toSysDictItemVO(dictItem);
        return ZaxResult.ok(sysDictItemVO);
    }

    @GetMapping(value = "/item/sub/{parentId}")
    public ZaxResult<List<SysDictItemVO>> listSubDictItem(@PathVariable("parentId") Integer parentId) {
        List<SysDictItem> itemsByDictId = sysDictService.getItemsByParentId(parentId);
        List<SysDictItemVO> sysDictItemVOs = dictItemBeanMapper.toSysDictItemVOs(itemsByDictId);
        return ZaxResult.ok(sysDictItemVOs);
    }

    @GetMapping(value = "/item/tree/id/{dictId}")
    public ZaxResult<SysDictItemTree> itemTree(@PathVariable("dictId") Integer dictId) {
        SysDictItemTree dictItemTreeByDictId = sysDictService.getDictItemTreeByDictId(dictId);
        return ZaxResult.ok(dictItemTreeByDictId);
    }

    @GetMapping(value = "/item/tree/code/{dictCode}")
    public ZaxResult<SysDictItemTree> itemTree(@PathVariable("dictCode") String dictCode) {
        SysDictItemTree dictItemTreeByDictId = sysDictService.getDictItemTreeByDictCode(dictCode);
        return ZaxResult.ok(dictItemTreeByDictId);
    }
}
