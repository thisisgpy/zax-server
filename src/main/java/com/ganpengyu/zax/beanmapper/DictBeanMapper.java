package com.ganpengyu.zax.beanmapper;

import com.ganpengyu.zax.model.SysDict;
import com.ganpengyu.zax.web.vo.request.SysDictCreateVO;
import com.ganpengyu.zax.web.vo.request.SysDictSearchVO;
import com.ganpengyu.zax.web.vo.request.SysDictUpdateVO;
import com.ganpengyu.zax.web.vo.response.SysDictVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * @author Pengyu Gan
 * CreateDate 2025/3/17
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DictBeanMapper {

    SysDict map(SysDict source, @MappingTarget SysDict dest);

    SysDict toSysDict(SysDictCreateVO sysDictCreateVO);

    SysDict toSysDict(SysDictUpdateVO sysDictUpdateVO);

    SysDict toSysDict(SysDictSearchVO sysDictSearchVO);

    SysDictVO toSysDictVO(SysDict sysDict);

    List<SysDictVO> toSysDictVOList(List<SysDict> sysDictList);

}
