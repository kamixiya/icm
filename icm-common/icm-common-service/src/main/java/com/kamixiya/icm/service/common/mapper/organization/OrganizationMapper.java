package com.kamixiya.icm.service.common.mapper.organization;

import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * OrganizationMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {

    /**
     * 将organization转换为organizationDTO，没有children和parent
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param organization 实体 organization
     * @param context      上下文
     * @return 转换后的DTO
     */
    OrganizationDTO toDTO(Organization organization, @Context CycleAvoidingMappingContext context);

    /**
     * 将OrganizationDTO转换为Organization
     *
     * @param organizationDTO DTO
     * @param context         上下文
     * @return 实体
     */
    Organization toEntity(OrganizationDTO organizationDTO, @Context CycleAvoidingMappingContext context);

    /**
     * 实体集合转换为DTO集合
     *
     * @param organizations 实体集合
     * @param context       上下文
     * @return 实体DTO集合信息
     */
    List<OrganizationDTO> toListDTO(List<Organization> organizations, @Context CycleAvoidingMappingContext context);

    /**
     * 将v集合转换为OrganizationDTO集合
     *
     * @param organizations 实体
     * @param context       上下文
     * @return 转换后的DTO
     */
    Set<OrganizationDTO> toSetDTO(Set<Organization> organizations, @Context CycleAvoidingMappingContext context);

}
