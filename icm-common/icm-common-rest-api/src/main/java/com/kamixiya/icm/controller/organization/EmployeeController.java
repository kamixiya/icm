package com.kamixiya.icm.controller.organization;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.service.common.service.organization.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EmployeeController
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"员工管理"})
@RestController
@RequestMapping("/api/organization/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "通过ID查询员工")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @GetMapping(value = "/{id}")
    public EmployeeDTO findById(
            @ApiParam(value = "员工ID", required = true) @PathVariable(name = "id") String id) {
        return employeeService.findById(id);
    }

    @ApiOperation(value = "新建员工")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_CREATE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @PostMapping(value = "")
    public EmployeeDTO create(
            @ApiParam(value = "员工信息", required = true) @RequestBody @Validated EmployeeEditInfoDTO employeeEditInfoDTO) {
        return employeeService.create(employeeEditInfoDTO);
    }

    @ApiOperation(value = "修改员工")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_UPDATE')")
    @Json(type = OrganizationDTO.class, filter = "parent")
    @PutMapping(value = "/{id}")
    public EmployeeDTO update(
            @ApiParam(value = "员工ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "员工信息", required = true) @RequestBody @Validated EmployeeEditInfoDTO employeeEditInfoDTO) {
        return employeeService.update(id, employeeEditInfoDTO);
    }

    @ApiOperation(value = "删除员工")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "员工ID", required = true) @PathVariable(name = "id") String id) {
        employeeService.delete(id);
    }

    @ApiOperation(value = "员工绑定用户")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_UPDATE')")
    @PutMapping(value = "/{employeeId}/user")
    public EmployeeDTO bindUser(
            @ApiParam(value = "员工ID", required = true) @PathVariable(name = "employeeId") String employeeId,
            @ApiParam(value = "用户ID", required = true) @RequestBody @Validated SimpleDataDTO<String> userId) {
        return employeeService.bindUser(employeeId, userId.getData());
    }

    @ApiOperation(value = "检查员工编号唯一性")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_UPDATE')")
    @GetMapping(value = "{code}/only")
    public SimpleDataDTO<Boolean> checkCode(
            @ApiParam(value = "员工ID, 新增员工时检查编号可用性可不填") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "员工编号", required = true) @PathVariable(value = "code") String code) {
        return employeeService.checkCode(id, code);
    }

    @ApiOperation(value = "查找所有员工")
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_RETRIEVE')")
    public List<EmployeeDTO> findAll(
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
            @ApiParam(value = "员工姓名") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "员工状态") @RequestParam(value = "available", required = false) Boolean available) {
        return employeeService.findAll(sort, name, available);
    }


}

