package com.kamixiya.icm.service.common.mapper.organization;

import com.kamixiya.icm.model.common.SimpleTreeDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.service.common.entity.organization.Organization;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * OrganizationTreeMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationTreeMapper {

    /**
     * 将Organization转换为SimpleTreeDataDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param entity  实体 Organization
     * @param context 上下文
     * @return 转换后的DTO
     */
    @Simple
    @Mapping(source = "organizationType", target = "type")
    SimpleTreeDataDTO toDTO(Organization entity, @Context CycleAvoidingMappingContext context);

    /**
     * 将OrganizationWithChildrenDTO转换为SimpleTreeDataDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param organizationDTO 实体DTO organizationDTO
     * @param context         上下文
     * @return 转换后的DTO
     */
    @Mapping(source = "organizationType", target = "type")
    SimpleTreeDataDTO toDTO(OrganizationDTO organizationDTO, @Context CycleAvoidingMappingContext context);


    /**
     * 转换List
     *
     * @param organizationDTOS 要转换的list
     * @param context          上下文
     * @return 转换后的DTO的list
     */
    List<SimpleTreeDataDTO> toListDTO(List<OrganizationDTO> organizationDTOS, @Context CycleAvoidingMappingContext context);


    /**
     * 转换set
     *
     * @param organizationDTOS 要转换的set
     * @param context          上下文
     * @return 转换后的DTO的set
     */
    Set<SimpleTreeDataDTO> toSetDTO(Set<OrganizationDTO> organizationDTOS, @Context CycleAvoidingMappingContext context);


}
