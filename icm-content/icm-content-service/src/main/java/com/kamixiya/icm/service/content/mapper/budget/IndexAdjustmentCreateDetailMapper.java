package com.kamixiya.icm.service.content.mapper.budget;

import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentCreateDetailEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.budget.adjustment.IndexAdjustmentCreateDetail;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * IndexAdjustmentCreateDetailMapper
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndexAdjustmentCreateDetailMapper {

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
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "index", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "showOrder", ignore = true)
    IndexAdjustmentCreateDetail toEntity(IndexAdjustmentCreateDetailEditInfoDTO dto, @Context CycleAvoidingMappingContext context);

}