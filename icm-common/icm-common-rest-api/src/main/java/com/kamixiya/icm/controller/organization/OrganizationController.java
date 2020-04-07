package com.kamixiya.icm.controller.organization;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.*;
import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.organization.OrgTreeRequestDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationType;
import com.kamixiya.icm.model.security.role.RoleDTO;
import com.kamixiya.icm.model.security.user.UserDTO;
import com.kamixiya.icm.security.CurrentUser;
import com.kamixiya.icm.service.common.service.organization.OrganizationQueryOptions;
import com.kamixiya.icm.service.common.service.organization.OrganizationService;
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
import java.util.Collection;
import java.util.List;

/**
 * OrganizationController
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"组织管理"})
@RestController
@RequestMapping("/api/organizations")
@Validated
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @ApiOperation(value = "查询组织机构树")
    @PostMapping("/organizationTree")
    @Json(type = SimpleTreeDataDTO.class, filter = "parent")
    List<SimpleTreeDataDTO> getOrganztionTree(@ApiParam(value = "组织机构请求实体") OrgTreeRequestDTO orgTreeDTO,
                                              @ApiParam(value = "员工数组") @RequestBody OrganizationRequstDataDTO<String[]> requstDataDTO) {

        return this.organizationService.findTree(requstDataDTO == null ? null : requstDataDTO.getEmployeeIds(), orgTreeDTO == null ? null : orgTreeDTO.getSelectedOrgId(), orgTreeDTO == null ? null : orgTreeDTO.getType(), requstDataDTO == null ? null : requstDataDTO.getOrganizationIds());
    }

    @ApiOperation(value = "查询子资源")
    @GetMapping("/{parentId}/children")
    @Json(type = OrganizationDTO.class, filter = "parent")
    List<OrganizationDTO> getChildren(@ApiParam(value = "通过父组织ID查询其所有子组织,父组织ID为空则查询所有组织", required = true) @PathVariable String parentId,
                                      @ApiParam(value = "是否读取所有子资源") @RequestParam(value = "deepLoad", required = false, defaultValue = "true") Boolean deepLoad,
                                      @ApiParam(value = "设定是否仅显示单一组织类型") @RequestParam(value = "exclusive", required = false, defaultValue = "false") Boolean exclusive,
                                      @ApiParam(value = "是否返回父组织") @RequestParam(value = "self", required = false, defaultValue = "false") Boolean self,
                                      @ApiParam(value = "是否内部") @RequestParam(value = "interior", required = false) Boolean interior,
                                      @ApiParam(value = "设定组织仅显示什么类型") @RequestParam(value = "organizationType", required = false) OrganizationType organizationType,
                                      @ApiParam(value = "组织名称") @RequestParam(value = "name", required = false) String name) {
        return organizationService.getChildren("null".equalsIgnoreCase(parentId) ? null : parentId, deepLoad, exclusive, organizationType, self, interior, name);
    }

    @ApiOperation(value = "更换父组织")
    @PutMapping("/{childId}/parent")
    @PreAuthorize("hasAnyAuthority('ORGANIZATION_UPDATE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    List<OrganizationDTO> changeParent(@ApiParam(value = "子组织ID", required = true) @PathVariable String childId,
                                       @ApiParam(value = "父组织ID与有序的组织ID集合", required = true) @RequestBody ObjectChangeParentDataDTO objectChangeParentDataDTO) {
        return organizationService.changeParent(childId, objectChangeParentDataDTO.getParentId(), objectChangeParentDataDTO.getIds());
    }

    @ApiOperation(value = "查询组织下的员工,若该组织有子组织那么子组织员工将一并查出")
    @GetMapping("/{organizationId}/employees")
    @Json(type = OrganizationDTO.class, filter = "children")
    PageDataDTO<EmployeeDTO> getEmployees(@ApiParam(value = "组织ID", required = true) @PathVariable String organizationId,
                                          @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
                                          @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
                                          @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false) String sort,
                                          @ApiParam(value = "是否读取所有子组织员工") @RequestParam(value = "deepLoad", required = false, defaultValue = "true") Boolean deepLoad,
                                          @ApiParam(value = "员工姓名") @RequestParam(value = "name", required = false) String name,
                                          @ApiParam(value = "员工账号") @RequestParam(value = "account", required = false) String account,
                                          @ApiParam(value = "员工状态") @RequestParam(value = "available", required = false) Boolean available) {
        OrganizationQueryOptions organizationQueryOptions = new OrganizationQueryOptions(organizationId, deepLoad, name, account, available);
        return organizationService.getEmployees(page, size, sort, organizationQueryOptions);
    }

    @ApiOperation(value = "查询所有组织(不分页)")
    @GetMapping(value = "/all")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    public List<OrganizationDTO> findAll(
            @ApiParam(value = "组织名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "设定组织仅显示什么类型") @RequestParam(value = "organizationType", required = false) OrganizationType organizationType) {
        return organizationService.findAll(name, organizationType);
    }

    @ApiOperation(value = "根据组织id和组织类型获取最近的组织类型所对应的组织")
    @GetMapping(value = "/recently/{organizationId}")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    public OrganizationDTO getRecentlyOrganization(
            @ApiParam(value = "组织id", required = true) @PathVariable String organizationId,
            @ApiParam(value = "组织类型") @RequestParam(value = "organizationType", required = false, defaultValue = "UNIT") OrganizationType organizationType) {
        return organizationService.getRecentlyOrganization(organizationId, organizationType);
    }


    @ApiOperation(value = "分页查询所有组织")
    @GetMapping(value = "", params = {"page", "size", "sort"})
    @PreAuthorize("hasAnyAuthority('ORGANIZATION_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @Json(type = OrganizationDTO.class, filter = "children")
    public PageDataDTO<OrganizationDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "组织名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "设定组织仅显示什么类型") @RequestParam(value = "organizationType", required = false) OrganizationType organizationType) {
        return organizationService.findOnePage(page, size, sort, name, organizationType);
    }

    @ApiOperation(value = "根据组织ID查找组织所分配的角色")
    @GetMapping(value = "/{id}/roles")
    @PreAuthorize("hasAnyAuthority('ORGANIZATION_RETRIEVE')")
    @Json(type = OrganizationDTO.class, include = "id,name")
    public List<RoleDTO> getRoles(
            @ApiParam(value = "组织ID", required = true) @PathVariable(name = "id") String id) {
        return organizationService.getRoles(id);
    }

    @ApiOperation(value = "给组织重新分配角色(组织必须是岗位才能分角色)")
    @PutMapping(value = "/{id}/roles")
    @PreAuthorize("hasAnyAuthority('ORGANIZATION_UPDATE')")
    public void grantRoles(
            @ApiParam(value = "组织ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "角色ID数组", required = true) @RequestBody @NotNull SimpleDataDTO<String[]> roles) {
        organizationService.grantRoles(id, roles.getData());
    }

    @ApiOperation(value = "切换组织")
    @GetMapping("/working-organization/{organizationId}")
    public Collection<String> setWorkingOrganization(@ApiParam(value = "组织id", required = true) @PathVariable String organizationId) {
        Collection<String> organizationAuthorities = null;
        CurrentUser currentUser = CurrentUser.getCurrentUser();
        if (currentUser != null) {
            organizationAuthorities = organizationService.getOrganizationAuthorities(currentUser.getUserId(), organizationId);
//            currentUser.setWorkingOrganizationAuthorities(organizationAuthorities);
//            currentUser.setWorkingOrganizationId(organizationId);
            UserDTO userDTO = currentUser.getUser();
            userDTO.setUnitDTO(organizationService.getUnitOrgId(organizationId));
            userDTO.setDepartmentDTO(organizationService.getDepartmentByOrgId(organizationId));
            userDTO.setPositionDTO(organizationService.getPositionOrgId(organizationId));
        }
        return organizationAuthorities;
    }

}
