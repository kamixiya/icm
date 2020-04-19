package com.kamixiya.icm.service.content.mapper.budget;

import com.kamixiya.icm.model.content.budget.general.GeneralProjectCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProject;
import com.kamixiya.icm.persistence.content.entity.budget.general.GeneralProjectDetail;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * GeneralProjectMapper
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, imports = {Arrays.class})
public interface GeneralProjectMapper {
    /**
     * 实体类转换为DTO类
     *
     * @param entity  实体
     * @param context 内容
     * @return DTO
     */
    @Simple
    GeneralProjectDTO toDTO(GeneralProject entity, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "id", ignore = true)
    GeneralProjectDetail copyGeneralProjectDetailEntity(GeneralProjectDetail entity);
    /**
     * 实体集合转换为DTO集合
     *
     * @param entity 实体集合
     * @param context        内容
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<GeneralProjectDTO> toList(Collection<GeneralProject> entity, @Context CycleAvoidingMappingContext context);

    /**
     * 将DTO转换为实体类
     *
     * @param dto     DTO数据
     * @param context 上下文
     * @return 实体数据
     */
    @Simple
    GeneralProject toEntity(GeneralProjectDTO dto, @Context CycleAvoidingMappingContext context);

    @Simple
    GeneralProject toEntity(GeneralProjectCreateInfoDTO dto, @Context CycleAvoidingMappingContext context);

    /**
     * 更新一个已经存在的实体数据
     *
     * @param dto     DTO
     * @param entity  实体数据
     * @param context 上下文
     */
    void updateEntity(GeneralProjectDTO dto, @MappingTarget GeneralProject entity, @Context CycleAvoidingMappingContext context);

    void updateEntity(GeneralProjectEditInfoDTO dto, @MappingTarget GeneralProject entity, @Context CycleAvoidingMappingContext context);
}
