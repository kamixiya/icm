package com.kamixiya.icm.service.content.mapper.budget;

import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentDetailDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentDetailEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.budget.adjustment.IndexAdjustmentDetail;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * IndexAdjustmentDetailMapper
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndexAdjustmentDetailMapper {

    /**
     * 将DTO转换为实体类
     *
     * @param dto     DTO数据
     * @param context 上下文
     * @return 实体数据
     */
    @Simple
    @Mapping(target = "adjustment", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "detailType", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "indexLibrary", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "showOrder", ignore = true)
    IndexAdjustmentDetail toEntity(IndexAdjustmentDetailEditInfoDTO dto, @Context CycleAvoidingMappingContext context);

    /**
     * 实体转DTO
     *
     * @param entity 实体
     * @return 详细信息
     */
    IndexAdjustmentDetailDTO toDTO(IndexAdjustmentDetail entity);

}

