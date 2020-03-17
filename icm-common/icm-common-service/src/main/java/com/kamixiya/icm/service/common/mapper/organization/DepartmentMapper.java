package com.kamixiya.icm.service.common.mapper.organization;

import com.kamixiya.icm.model.organization.department.DepartmentBaseDTO;
import com.kamixiya.icm.model.organization.department.DepartmentDTO;
import com.kamixiya.icm.model.organization.department.DepartmentEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Department;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * DepartmentMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    /**
     * 将Department转换为DepartmentDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param entity 实体Department
     * @return 转换后的DTO
     */
    @Simple
    DepartmentDTO toDTO(Department entity);

    /**
     * 实体转基础dto
     *
     * @param entity 实体
     * @return 基础dto
     */
    DepartmentBaseDTO toBaseDTO(Department entity);

    /**
     * 转换创建信息到实体类
     *
     * @param departmentEditInfoDTO 创建的Department
     * @return 实体类
     */
    Department toEntity(DepartmentEditInfoDTO departmentEditInfoDTO);


    /**
     * 转换修改信息到实体类
     *
     * @param departmentEditInfoDTO 要更新的信息
     * @param department            实体数据
     */
    void updateEntity(DepartmentEditInfoDTO departmentEditInfoDTO, @MappingTarget Department department);

    /**
     * 将List<Department>转换为List<DepartmentDTO>
     *
     * @param departments 单位集合
     * @return 转换后的单位DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<DepartmentDTO> toDTOList(List<Department> departments);

}
