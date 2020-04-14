package com.kamixiya.icm.service.common.mapper.security;

import com.kamixiya.icm.model.security.role.RoleBaseDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.role.RoleEditInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.service.common.entity.security.Role;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * RoleMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    /**
     * 转换创建信息到实体类
     *
     * @param createInfo 创建的Role
     * @return 实体类
     */
    Role toEntity(RoleEditInfoDTO createInfo);

    /**
     * 转换修改信息到实体类
     *
     * @param updateInfo 要更新的信息
     * @param role       实体数据
     */
    void updateEntity(RoleEditInfoDTO updateInfo, @MappingTarget Role role);

    /**
     * DTO转换实体类
     *
     * @param roleDTO dto
     * @return 实体
     */
    Role toDTOEntity(RoleDTO roleDTO);

    /**
     * 实体类转换为DTO类
     *
     * @param role 实体
     * @return DTO
     */
    @Simple
    RoleDTO toDTO(Role role);

    /**
     * 实体集合转换为DTO集合
     *
     * @param roles 实体集合
     * @return 实体DTO集合信息
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<RoleDTO> toSetDTO(Set<Role> roles);

    /**
     * 实体集合转换为DTO集合
     *
     * @param roles 实体集合
     * @return 实体DTO集合信息
     */
    Set<RoleBaseDTO> toSetBaseDTO(Set<Role> roles);

    /**
     * 将Role集合转换为RoleDTO集合
     *
     * @param role 角色
     * @return 转换后的DTO
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<RoleDTO> toListRoleDTO(List<Role> role);

    /**
     * 将User集合转换为UserDTO集合
     *
     * @param users 用户
     * @return 转换后的DTO
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<UserDTO> toSetUserDto(Set<User> users);


}