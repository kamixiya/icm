package com.kamixiya.icm.controller.content.budget;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import com.kamixiya.icm.service.content.service.budget.IndexLibraryService;
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
 * @date 2020/4/17
 */
@Api(tags = {"指标库管理"})
@RestController
@RequestMapping("/api/icm-budget/indexes")
@Validated
public class IndexLibraryController {

    private final IndexLibraryService indexLibraryService;

    @Autowired
    public IndexLibraryController(IndexLibraryService indexLibraryService) {
        this.indexLibraryService = indexLibraryService;
    }

    @ApiOperation(value = "分页查询指标")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('INDEX_LIBRARY_RETRIEVE')")
    public PageDataDTO<IndexLibraryDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "项目名称，支持模糊查询") @RequestParam(value = "projectName", required = false) String projectName,
            @ApiParam(value = "预算单位") @RequestParam(value = "unitId", required = false) String unitId,
            @ApiParam(value = "部门") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "预算指标名称") @RequestParam(value = "indexProjectName", required = false) String indexProjectName
    ) {
        return indexLibraryService.findOnePage(page, size, sort, projectName, unitId, departmentId, indexProjectName);
    }

    @ApiOperation(value = "根据条件查询全部指标")
    @GetMapping(value = "/all")
    public List<IndexLibraryDTO> findAll(
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "预算年度", required = true) @RequestParam(value = "year") String year,
            @ApiParam(value = "申报部门") @RequestParam(value = "departmentName", required = false) String departmentName,
            @ApiParam(value = "申报部门ID") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "申报单位") @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(value = "项目名称，支持模糊查询") @RequestParam(value = "projectName", required = false) String projectName,
            @ApiParam(value = "项目编号") @RequestParam(value = "projectCode", required = false) String projectCode
    ) {
        return indexLibraryService.findAll(sort, year, projectName, projectCode, departmentName, departmentId, unitName);
    }

    @ApiOperation(value = "根据ID查询指标信息")
    @PreAuthorize("hasAnyAuthority('INDEX_LIBRARY_RETRIEVE')")
    @GetMapping(value = "{id}")
    public IndexLibraryDTO findById(
            @ApiParam(value = "指标Id", required = true) @PathVariable(name = "id") String id
    ) {
        return indexLibraryService.findById(id);
    }


}

