package com.kamixiya.icm.service.common.mapper.organization;

import com.kamixiya.icm.model.organization.unit.UnitBaseDTO;
import com.kamixiya.icm.model.organization.unit.UnitDTO;
import com.kamixiya.icm.model.organization.unit.UnitEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Unit;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

/**
 * UnitMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {
    /**
     * 将Unit转换为UnitDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param entity 实体Unit
     * @return 转换后的DTO
     */
    @Simple
    UnitDTO toDTO(Unit entity);

    /**
     * 实体转基础dto
     *
     * @param entity 实体
     * @return 基础dto
     */
    UnitBaseDTO toBaseDTO(Unit entity);

    /**
     * 将UnitDTO转换为Unit
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param dto DTO数据
     * @return 转换后的实体
     */
    Unit toEntity(UnitDTO dto);

    /**
     * 转换创建信息到实体类
     *
     * @param dto 创建的Unit
     * @return 实体类
     */
    Unit toEntity(UnitEditInfoDTO dto);


    /**
     * 转换修改信息到实体类
     *
     * @param dto  要更新的信息
     * @param unit 实体数据
     */
    void updateEntity(UnitEditInfoDTO dto, @MappingTarget Unit unit);

    /**
     * 将List<Unit>转换为List<UnitDTO>
     *
     * @param units 单位集合
     * @return 转换后的单位DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<UnitDTO> toDTOList(List<Unit> units);

    /**
     * 将Set<Unit>转换为Set<UnitDTO>
     *
     * @param units 单位集合
     * @return 转换后的单位DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<UnitDTO> toDTOSet(Set<Unit> units);
}