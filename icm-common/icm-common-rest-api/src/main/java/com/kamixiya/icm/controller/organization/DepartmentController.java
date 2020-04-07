package com.kamixiya.icm.controller.organization;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.organization.department.DepartmentDTO;
import com.kamixiya.icm.model.organization.department.DepartmentEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.service.common.service.organization.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * DepartmentController
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"部门管理"})
@RestController
@RequestMapping("/api/organization/departments")
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "通过ID查询部门")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public DepartmentDTO findById(
            @ApiParam(value = "部门ID", required = true) @PathVariable(name = "id") String id) {
        return departmentService.findById(id);
    }

    @ApiOperation(value = "新建部门")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT_CREATE')")
    @Json(type = OrganizationDTO.class, filter = "children")
    @PostMapping(value = "")
    public OrganizationDTO create(
            @ApiParam(value = "部门信息", required = true) @RequestBody @Validated DepartmentEditInfoDTO departmentEditInfoDTO) {
        return departmentService.create(departmentEditInfoDTO);
    }

    @ApiOperation(value = "修改部门")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT_UPDATE')")
    @PutMapping(value = "/{id}")
    public DepartmentDTO update(
            @ApiParam(value = "部门ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "部门信息", required = true) @RequestBody @Validated DepartmentEditInfoDTO departmentEditInfoDTO) {
        return departmentService.update(id, departmentEditInfoDTO);
    }

    @ApiOperation(value = "删除部门")
    @PreAuthorize("hasAnyAuthority('DEPARTMENT_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "部门ID", required = true) @PathVariable(name = "id") String id) {
        departmentService.delete(id);
    }


}
