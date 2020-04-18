package com.kamixiya.icm.controller.content.budget;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.service.content.service.budget.GeneralProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Api(tags = {"项目库管理"})
@RestController
@RequestMapping("/api/icm-budget/general-projects")
@Validated
public class GeneralProjectController {

    private final GeneralProjectService generalProjectService;

    @Autowired
    public GeneralProjectController(GeneralProjectService generalProjectService) {
        this.generalProjectService = generalProjectService;
    }

    @ApiOperation(value = "分页查询项目库信息")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('GENERAL_PROJECT_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "children")
    public PageDataDTO<GeneralProjectDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "申报部门") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "项目名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "项目类别") @RequestParam(value = "projectType", required = false) String projectType,
            @ApiParam(value = "项目类别明细") @RequestParam(value = "detailedProjectType", required = false) String detailedProjectType,
            @ApiParam(value = "项目编号") @RequestParam(value = "code", required = false) String code,
            @ApiParam(value = "申报年份") @RequestParam(value = "year", required = false) String year
    ) {
        return generalProjectService.findOnePage(page, size, sort, departmentId, name, projectType, detailedProjectType, code, year);
    }

    @ApiOperation(value = "根据ID查询项目库")
    @PreAuthorize("hasAnyAuthority('GENERAL_PROJECT_RETRIEVE')")
    @GetMapping(value = "{id}")
    @Json(type = OrganizationDTO.class, filter = "children")
    public GeneralProjectDTO findById(
            @ApiParam(value = "项目Id", required = true) @PathVariable(name = "id") String id
    ) {
        return generalProjectService.findById(id);
    }

    @ApiOperation(value = "新建项目库")
    @PreAuthorize("hasAnyAuthority('GENERAL_PROJECT_CREATE')")
    @PostMapping(value = "")
    @Json(type = OrganizationDTO.class, filter = "children")
    public GeneralProjectDTO create(
            @ApiParam(value = "项目库编辑信息", required = true) @RequestBody @Validated GeneralProjectCreateInfoDTO generalProjectCreateInfo
    ) {
        return generalProjectService.create(generalProjectCreateInfo);
    }

    @ApiOperation(value = "提交项目库")
    @PreAuthorize("hasAnyAuthority('GENERAL_PROJECT_UPDATE','GENERAL_PROJECT_AUDIT')")
    @PutMapping(value = "/{id}")
    @Json(type = OrganizationDTO.class, filter = "children")
    public GeneralProjectDTO complete(
            @ApiParam(value = "项目Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "项目库编辑信息", required = true) @RequestBody @Validated GeneralProjectEditInfoDTO generalProjectEditInfo
    ) {
        return generalProjectService.complete(id, generalProjectEditInfo);
    }

    @ApiOperation(value = "删除项目库")
    @PreAuthorize("hasAnyAuthority('GENERAL_PROJECT_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "项目Id", required = true) @PathVariable(name = "id") String id
    ) {
        generalProjectService.delete(id);
    }

    @ApiOperation(value = "查询全部项目库信息")
    @GetMapping(value = "/all")
    @Json(type = OrganizationDTO.class, filter = "children")
    public List<GeneralProjectDTO> findAll(
            @ApiParam(value = "申报单位") @RequestParam(value = "unitId", required = false) String unitId,
            @ApiParam(value = "申报部门") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "项目名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "项目类别") @RequestParam(value = "projectType", required = false) String projectType,
            @ApiParam(value = "项目类别明细") @RequestParam(value = "detailedProjectType", required = false) String detailedProjectType
    ) {
        return generalProjectService.findAll(unitId, departmentId, name, projectType, detailedProjectType);
    }
}
