package com.kamixiya.icm.service.content.mapper.contract;

import com.kamixiya.icm.model.content.purchase.AttachEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusionAttach;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


/**
 * 合同订立附件实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractConclusionAttachMapper {

    /**
     * 编辑信息转实体
     *
     * @param createInfoDTO 编辑信息
     * @return 实体
     */
    @Simple
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "contractConclusion", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "showOrder", ignore = true)
    @Mapping(target = "files", ignore = true)
    ContractConclusionAttach toEntity(AttachEditInfoDTO createInfoDTO);


}
