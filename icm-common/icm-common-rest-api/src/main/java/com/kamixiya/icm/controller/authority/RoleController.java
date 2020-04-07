package com.kamixiya.icm.controller.authority;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.role.RoleEditInfoDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.service.common.service.security.RoleService;
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
import java.util.Set;

/**
 * RoleController
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"角色管理"})
@RestController
@RequestMapping("/api/authority/roles")
@Validated
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "通过ID查询角色")
    @PreAuthorize("hasAnyAuthority('ROLE_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @GetMapping(value = "/{id}")
    public RoleDTO findById(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id) {
        return roleService.findById(id);
    }

    @ApiOperation(value = "取得角色分配的用户")
    @PreAuthorize("hasAnyAuthority('ROLE_RETRIEVE')")
    @GetMapping(value = "/{id}/users")
    public Set<UserDTO> findUsers(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id) {
        return roleService.findUsers(id);
    }

    @ApiOperation(value = "取得角色分配的权限")
    @PreAuthorize("hasAnyAuthority('ROLE_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @GetMapping(value = "/{id}/authorities")
    public Set<AuthorityDTO> findAuthorities(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id) {
        return roleService.findAuthorities(id);
    }

    @ApiOperation(value = "取得角色分配的组织")
    @PreAuthorize("hasAnyAuthority('ROLE_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    @GetMapping(value = "/{id}/organizations")
    public Set<OrganizationDTO> findOrganizations(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id) {
        return roleService.findOrganizations(id);
    }

    @ApiOperation(value = "分页查询角色")
    @PreAuthorize("hasAnyAuthority('ROLE_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    @GetMapping(value = "", params = {"page", "size", "sort"})
    public PageDataDTO<RoleDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "名称, 支持模糊查找") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "true就是所有,false是当前用户") @RequestParam(value = "isAll", required = false, defaultValue = "true") Boolean isAll) {
        return roleService.findOnePage(page, size, sort, name, isAll);
    }

    @ApiOperation(value = "新建角色")
    @PreAuthorize("hasAnyAuthority('ROLE_CREATE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    @PostMapping(value = "")
    public RoleDTO create(
            @ApiParam(value = "角色信息", required = true) @RequestBody @Validated RoleEditInfoDTO roleEditInfoDTO) {
        return roleService.create(roleEditInfoDTO);
    }

    @ApiOperation(value = "分配用户")
    @PreAuthorize("hasAnyAuthority('ROLE_UPDATE')")
    @PutMapping(value = "/{id}/users")
    public void grantUsers(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "用户ID数组", required = true) @RequestBody @NotNull SimpleDataDTO<String[]> users) {
        roleService.grantUsers(id, users.getData());
    }

    @ApiOperation(value = "分配权限")
    @PreAuthorize("hasAnyAuthority('ROLE_UPDATE')")
    @PutMapping(value = "/{id}/authorities")
    public void grantAuthorities(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "权限ID数组", required = true) @RequestBody @NotNull SimpleDataDTO<String[]> authorities,
            @ApiParam(value = "true就是所有,false是当前用户") @RequestParam(value = "isAll", required = false, defaultValue = "true") Boolean isAll) {
        roleService.grantAuthorities(id, authorities.getData());
    }

    @ApiOperation(value = "分配组织")
    @PreAuthorize("hasAnyAuthority('ROLE_UPDATE')")
    @PutMapping(value = "/{id}/organizations")
    public void grantOrganizations(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "组织ID数组", required = true) @RequestBody @NotNull SimpleDataDTO<String[]> organizations) {
        roleService.grantOrganizations(id, organizations.getData());
    }

    @ApiOperation(value = "修改角色")
    @PreAuthorize("hasAnyAuthority('ROLE_UPDATE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    @PutMapping(value = "/{id}")
    public RoleDTO update(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "角色信息", required = true) @RequestBody @Validated RoleEditInfoDTO roleEditInfoDTO) {
        return roleService.update(id, roleEditInfoDTO);
    }

    @ApiOperation(value = "删除角色")
    @PreAuthorize("hasAnyAuthority('ROLE_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "角色ID", required = true) @PathVariable(name = "id") String id) {
        roleService.delete(id);
    }

    @ApiOperation(value = "查询角色名称是否可用")
    @PreAuthorize("hasAnyAuthority('ROLE_UPDATE,ROLE_CREATE')")
    @GetMapping(value = "/availability")
    public SimpleDataDTO<Boolean> checkNameAvailability(
            @ApiParam(value = "角色ID, 新增时检查角色名称时可不填") @RequestParam(value = "id", required = false) String roleId,
            @ApiParam(value = "名称", required = true) @RequestParam(value = "name") String name) {
        SimpleDataDTO<Boolean> simpleDataDTO = new SimpleDataDTO<>();
        simpleDataDTO.setData(roleService.checkNameAvailability(roleId, name));
        return simpleDataDTO;
    }


}
