package com.kamixiya.icm.service.content.mapper.contract;

import com.kamixiya.icm.model.content.contract.ContractConclusionCreateInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusion;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.*;

import java.util.List;

/**
 * 合同订立实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContractConclusionMapper {

    /**
     * 实体转DTO
     *
     * @param entity  实体信息
     * @param context 上下文
     * @return DTO
     */
    @Simple
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "lastModifyUser", ignore = true)
    ContractConclusionDTO toDTO(ContractConclusion entity, @Context CycleAvoidingMappingContext context);

    /**
     * 实体集合转DTO集合
     *
     * @param list    实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<ContractConclusionDTO> toList(List<ContractConclusion> list);

    /**
     * 编辑信息转实体
     *
     * @param createInfoDTO 编辑信息
     * @return 实体
     */
    @Simple
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "declarer", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "purchaseReport", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "indexes", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "payees", ignore = true)
    @Mapping(target = "attaches", ignore = true)
    ContractConclusion toEntity(ContractConclusionCreateInfoDTO createInfoDTO);

    /**
     * 通过编辑信息更新实体
     *
     * @param editInfoDTO 编辑信息
     * @param entity      实体数据
     */
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "declarer", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "purchaseReport", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "indexes", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "payees", ignore = true)
    @Mapping(target = "attaches", ignore = true)
    void updateEntity(ContractConclusionEditInfoDTO editInfoDTO, @MappingTarget ContractConclusion entity);

    /**
     * 合同结转 - 订立
     * @param dto 合同结转-订立dto
     * @param context 上下文
     * @return
     */
    ContractConclusion toEntity(ContractConclusionDTO dto, @Context CycleAvoidingMappingContext context);

    @Simple
    @Mapping(target = "id", ignore = true)
    ContractConclusion toEntityWithoutId(ContractConclusionDTO dto, @Context CycleAvoidingMappingContext context);

}
