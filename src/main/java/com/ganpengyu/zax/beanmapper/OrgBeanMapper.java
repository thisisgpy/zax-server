package com.ganpengyu.zax.beanmapper;

import com.ganpengyu.zax.model.SysOrg;
import com.ganpengyu.zax.service.dto.SysOrgTree;
import com.ganpengyu.zax.web.vo.request.SysOrgCreateVO;
import com.ganpengyu.zax.web.vo.request.SysOrgUpdateVO;
import com.ganpengyu.zax.web.vo.response.SysOrgVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

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

    SysOrg toSysOrg(SysOrgCreateVO sysOrgCreateVO);

    SysOrg toSysOrg(SysOrgUpdateVO sysOrgUpdateVO);

    SysOrgVO toSysOrgVO(SysOrg sysOrg);

    List<SysOrgVO> toSysOrgVOList(List<SysOrg> source);
}
