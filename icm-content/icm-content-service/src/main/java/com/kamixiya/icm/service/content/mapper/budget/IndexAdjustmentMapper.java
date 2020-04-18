package com.kamixiya.icm.service.content.mapper.budget;

import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.budget.adjustment.IndexAdjustment;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.*;

import java.util.List;

/**
 * IndexAdjustmentMapper
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndexAdjustmentMapper {

    /**
     * 将DTO转换为实体类
     *
     * @param dto     DTO数据
     * @param context 上下文
     * @return 实体数据
     */
    @Simple
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instanceId", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "unit", ignore = true)
    IndexAdjustment toEntity(IndexAdjustmentCreateInfoDTO dto, @Context CycleAvoidingMappingContext context);

    /**
     * 实体类转换为DTO类
     *
     * @param entity 实体
     * @param context 上下文
     * @return DTO
     */
    @Simple
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "lastModifyUser", ignore = true)
    IndexAdjustmentDTO toDTO(IndexAdjustment entity, @Context CycleAvoidingMappingContext context);

    /**
     * 实体集合转换为DTO集合
     *
     * @param entity 实体集合
     * @param context 上下文
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<IndexAdjustmentDTO> toList(List<IndexAdjustment> entity, @Context CycleAvoidingMappingContext context);

    /**
     * 更新一个已经存在的实体数据
     *
     * @param dto    DTO
     * @param entity 实体数据
     */
    @Mapping(target = "createDetails", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "instanceId", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "unit", ignore = true)
    void updateEntity(IndexAdjustmentEditInfoDTO dto, @MappingTarget IndexAdjustment entity);
}
