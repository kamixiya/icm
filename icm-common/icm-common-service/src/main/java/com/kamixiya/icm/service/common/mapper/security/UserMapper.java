package com.kamixiya.icm.service.common.mapper.security;

import com.kamixiya.icm.model.security.user.UserBaseDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.model.security.user.UserEditInfoDTO;
import com.kamixiya.icm.service.common.entity.security.User;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.*;

import java.util.Set;

/**
 * UserMapper
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * 将User转换为UserDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param entity 实体user
     * @return 转换后的DTO
     */
    @Mapping(source = "entity.employee", target = "assignedEmployee")
    UserDTO toDTO(User entity);

    /**
     * 将User转换为UserBaseDTO
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param entity 实体user
     * @return 转换后的DTO
     */
    UserBaseDTO toBaseDTO(User entity);

    /**
     * 将UserDTO转换为User
     * 如果有不同名的情况，需要使用@Mappings和@Mapping来定义
     *
     * @param dto DTO数据
     * @return 转换后的实体
     */
    User toEntity(UserDTO dto);

    /**
     * 使用DTO更新Entity
     *
     * @param dto    DTO数据
     * @param entity 要更新的实体
     */
    void updateEntity(UserEditInfoDTO dto, @MappingTarget User entity);

    /**
     * 转换创建信息到实体类
     *
     * @param createInfo 创建的User
     * @return 实体类
     */
    User toEntity(UserEditInfoDTO createInfo);

    /**
     * 将Set<User> 转换为 Set<UserWithEmployeeIdDTO>
     *
     * @param userSet 用户集合
     * @return Set<UserWithEmployeeIdDTO>
     */
    @IterableMapping(qualifiedBy = Simple.class)
    Set<UserDTO> toSet(Set<User> userSet);


}
