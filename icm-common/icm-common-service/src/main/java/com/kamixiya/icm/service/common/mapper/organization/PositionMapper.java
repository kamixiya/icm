package com.kamixiya.icm.service.common.mapper.organization;

import com.kamixiya.icm.model.organization.position.PositionBaseDTO;
import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.model.organization.position.PositionEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Position;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * PositionMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PositionMapper {
    /**
     * 将实体转换为DTO
     *
     * @param position 岗位实体
     * @return 岗位DTO
     */
    @Simple
    PositionDTO toDTO(Position position);

    /**
     * 将DTO转换为实体
     *
     * @param dto 岗位DTO
     * @return 岗位实体
     */
    Position toEntity(PositionDTO dto);

    /**
     * 将DTO转换为实体
     *
     * @param dto 岗位DTO
     * @return 岗位实体
     */
    Position toEntity(PositionEditInfoDTO dto);

    /**
     * 实体转基础dto
     *
     * @param entity 实体
     * @return 基础dto
     */
    PositionBaseDTO toBaseDTO(Position entity);


    /**
     * 转换修改信息到实体类
     *
     * @param dto        要更新的信息
     * @param department 实体数据
     */
    void updateEntity(PositionEditInfoDTO dto, @MappingTarget Position department);

    /**
     * 将List<Position>转换为List<PositionDTO>
     *
     * @param list 岗位集合
     * @return 转换后的岗位DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PositionDTO> toDTOList(List<Position> list);

    /**
     * 将Set<Position>转换为Set<PositionDTO>
     *
     * @param set 岗位集合
     * @return 转换后的岗位DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<PositionDTO> toDTOSet(Set<Position> set);

    /**
     * 将Set<Position>转换为List<PositionDTO>
     *
     * @param set 岗位集合
     * @return 转换后的岗位DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PositionBaseDTO> toDTOBaseList(Set<Position> set);

    /**
     * 将List<Position> 转换为List<PositionDTO>
     *
     * @param list list实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PositionDTO> toList(Set<Position> list);

    /**
     * 将List<Position> 转换为List<PositionDTO>
     *
     * @param list list实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PositionBaseDTO> toBaseList(List<PositionDTO> list);


}