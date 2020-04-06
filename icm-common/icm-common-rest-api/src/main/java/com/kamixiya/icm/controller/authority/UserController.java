package com.kamixiya.icm.controller.authority;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.user.PasswordInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.model.security.user.UserEditInfoDTO;
import com.kamixiya.icm.service.common.service.security.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

/**
 * 用户管理
 *
 * @author Zhu Jie
 * @date 2020/4/6
 */
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/api/authority/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "根据ID查找用户，用户包含基本资料，关联的员工资料，关联的工作岗位以及当前的用户设置")
    @PreAuthorize("hasAnyAuthority('USER_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public UserDTO findById(
            @ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") String id) {
        return userService.findById(id);
    }

    @ApiOperation(value = "分页查询用户")
    @PreAuthorize("hasAnyAuthority('USER_RETRIEVE')")
    @GetMapping(value = "", params = {"page", "size", "sort"})
    public PageDataDTO<UserDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "用户名,支持模糊查询") @RequestParam(value = "name", required = false) String name) {
        return userService.findOnePage(page, size, sort, name);
    }

    @ApiOperation(value = "查询用户(不分页)")
    @PreAuthorize("hasAnyAuthority('USER_RETRIEVE')")
    @GetMapping(value = "/all")
    public List<UserDTO> findAll(
            @ApiParam(value = "用户名") @RequestParam(value = "name", required = false) String name) {
        return userService.findAll(name);
    }

    @ApiOperation(value = "查询用户登录账号是否可用")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE,USER_CREATE,USER_RETRIEVE')")
    @GetMapping(value = "/availability")
    public SimpleDataDTO<Boolean> checkAccountAvailability(
            @ApiParam(value = "用户ID, 新增用户时检查账号可用性可不填") @RequestParam(value = "id", required = false) String userId,
            @ApiParam(value = "账号", required = true) @RequestParam(value = "account") String account) {
        return userService.checkAccountAvailability(userId, account);
    }

    @ApiOperation(value = "修改用户密码（只用于修改当前登录用户的密码）")
    @PutMapping(value = "/{userId}/password")
    public void changePassword(
            @ApiParam(value = "用户ID", required = true) @PathVariable(value = "userId") @Size(min = 1) String userId,
            @ApiParam(value = "密码信息", required = true) @RequestBody @Validated PasswordInfoDTO passwordInfoDTO) {
        userService.changePassword(userId, passwordInfoDTO);
    }

    @ApiOperation(value = "新建用户")
    @PreAuthorize("hasAnyAuthority('USER_CREATE')")
    @PostMapping(value = "")
    public UserDTO create(
            @ApiParam(value = "用户信息", required = true) @RequestBody @Validated UserEditInfoDTO userEditInfoDTO) {
        return userService.create(userEditInfoDTO);
    }

    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE')")
    @PutMapping(value = "/{id}")
    public UserDTO update(
            @ApiParam(value = "用户", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "用户信息", required = true) @RequestBody @Validated UserEditInfoDTO userEditInfoDTO) {
        return userService.update(id, userEditInfoDTO);
    }

    @ApiOperation(value = "删除用户")
    @PreAuthorize("hasAnyAuthority('USER_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") String id) {
        userService.delete(id);
    }

    @ApiOperation(value = "根据用户ID查找用户所分配的角色")
    @PreAuthorize("hasAnyAuthority('USER_RETRIEVE')")
    @GetMapping(value = "/{id}/roles")
    public List<RoleDTO> getRoles(
            @ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") String id) {
        return userService.getRoles(id);
    }

    @ApiOperation(value = "分配角色（重新设置用户的角色）")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE')")
    @PutMapping(value = "/{id}/roles")
    public void grantRoles(
            @ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "角色ID数组", required = true) @RequestBody @NotNull SimpleDataDTO<String[]> roles) {
        userService.grantRoles(id, roles.getData());
    }

    @ApiOperation(value = "解锁账号（因多次登录失败而被锁住账号)")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE')")
    @PutMapping(value = "/{id}/unlock")
    public void unlock(
            @ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") String id) {
        userService.resetLoginCountAfterSuccessfulLogin(id);
    }

    @ApiOperation(value = "切换组织（应用于用户所属的岗位可访问的组织架构数据）")
    @PutMapping("/{organizationId}/working-organization")
    public Collection<String> setWorkingOrganization(@ApiParam(value = "组织id", required = true) @PathVariable String organizationId) {
        return userService.setWorkingOrganization(organizationId);
    }

}
