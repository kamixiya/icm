package com.kamixiya.icm.service.common.mapper.organization;

import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeEditInfoDTO;
import com.kamixiya.icm.service.common.entity.organization.Employee;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * EmployeeMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    /**
     * 将Employee转换为EmployeeDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param entity 实体Employee
     * @return 转换后的DTO
     */
    @Simple
    EmployeeDTO toDTO(Employee entity);

    /**
     * 将EmployeeDTO转换为Employee
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param dto DTO数据
     * @return 转换后的实体
     */
    Employee toEntity(EmployeeDTO dto);

    /**
     * 转换创建信息到实体类
     *
     * @param employeeEditInfoDTO 创建的Employee
     * @return 实体类
     */
    Employee toEntity(EmployeeEditInfoDTO employeeEditInfoDTO);


    /**
     * 转换修改信息到实体类
     *
     * @param updateInfo 要更新的信息
     * @param employee   实体数据
     */
    void updateEntity(EmployeeEditInfoDTO updateInfo, @MappingTarget Employee employee);

    /**
     * 转换List<Employee>为List<EmployeeDTO>
     *
     * @param employeeList 员工集合
     * @return List<EmployeeDTO>
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<EmployeeDTO> toListEntity(List<Employee> employeeList);

    /**
     * 转换Set<Employee>为Set<EmployeeDTO>
     *
     * @param employeeSet 员工集合
     * @return List<EmployeeDTO>
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<EmployeeDTO> toDTOSet(Set<Employee> employeeSet);

    /**
     * UserWithEmployeeSimpleDTO将转换为EmployeeSimpleDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param dto UserWithEmployeeSimpleDTO
     * @return 转换后的DTO
     */
}
