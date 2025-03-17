package com.ganpengyu.zax.beanmapper;

import com.ganpengyu.zax.model.SysDict;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author Pengyu Gan
 * CreateDate 2025/3/17
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DictBeanMapper {

    SysDict map(SysDict source, @MappingTarget SysDict dest);

}
