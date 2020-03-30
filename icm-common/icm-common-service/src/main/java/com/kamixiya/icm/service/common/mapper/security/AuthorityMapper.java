package com.kamixiya.icm.service.common.mapper.security;

import com.kamixiya.icm.model.security.authority.AuthorityBaseDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.authority.AuthorityEditInfoDTO;
import com.kamixiya.icm.service.common.entity.security.Authority;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * AuthorityMapper
 *
 * @author Zhu Jie
 * @date 2020/3/30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {

    /**
     * 将Authority转换为AuthorityDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param authority 实体Authority
     * @return 转换后的DTO
     */
    AuthorityDTO toDTO(Authority authority);

    /**
     * 将Authority转换为AuthorityBaseDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param authority 实体Authority
     * @return 转换后的DTO
     */
    AuthorityBaseDTO toBaseDTO(Authority authority);

    /**
     * 将DTO转换为实体类
     *
     * @param authorityEditInfoDTO DTO数据
     * @return 转换后的实体类
     */
    Authority toEntity(AuthorityEditInfoDTO authorityEditInfoDTO);

    /**
     * 使用DTO更新Entity
     *
     * @param dto    DTO数据
     * @param entity 要更新的实体
     */
    void updateEntity(AuthorityEditInfoDTO dto, @MappingTarget Authority entity);

    /**
     * 将Authority集合转换为AuthorityDTO集合
     *
     * @param authorities 实体权限
     * @return 转换后的DTO
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<AuthorityDTO> toSetDTO(Set<Authority> authorities);

    /**
     * 将Authority集合转换为AuthorityDTO集合
     *
     * @param authorities 实体权限
     * @return 转换后的DTO
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<AuthorityDTO> toListDTO(List<Authority> authorities);

}