package com.ganpengyu.zax.beanmapper;

import com.ganpengyu.zax.model.SysDictItem;
import com.ganpengyu.zax.service.dto.SysDictItemTree;
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
public interface DictItemBeanMapper {

    SysDictItem map(SysDictItem source, @MappingTarget SysDictItem dest);

    List<SysDictItemTree> toSysDictItemTree(List<SysDictItem> source);

}
