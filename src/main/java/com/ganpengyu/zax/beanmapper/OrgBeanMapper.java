package com.ganpengyu.zax.beanmapper;

import com.ganpengyu.zax.model.SysOrg;
import com.ganpengyu.zax.service.dto.SysOrgTree;
import org.mapstruct.*;

import java.util.List;

/**
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrgBeanMapper {

    SysOrg map(SysOrg source, @MappingTarget SysOrg dest);

    List<SysOrgTree> toSysOrgTree(List<SysOrg> source);
}
