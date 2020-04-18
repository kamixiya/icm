package com.kamixiya.icm.controller.content.budget;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.persistence.content.entity.budget.adjustment.AdjustType;
import com.kamixiya.icm.service.content.service.budget.IndexAdjustmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Zhu Jie
 * @date 2020/4/17
 */
@Api(tags = {"指标（预算）调整管理"})
@RestController
@RequestMapping("/api/icm-budget/index-adjustment")
@Validated
public class IndexAdjustmentController {
    private static final String DATA_TYPE = "指标（预算）调整";

    private final IndexAdjustmentService indexAdjustmentService;

    @Autowired
    public IndexAdjustmentController(IndexAdjustmentService indexAdjustmentService) {
        this.indexAdjustmentService = indexAdjustmentService;
    }

    @ApiOperation(value = "分页查询指标（预算）调整信息")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('INDEX_ADJUSTMENT_RETRIEVE')")
    public PageDataDTO<IndexAdjustmentDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "年份") @RequestParam(value = "year", required = false) String year,
            @ApiParam(value = "项目编号，支持模糊查询") @RequestParam(value = "projectCode", required = false) String projectCode,
            @ApiParam(value = "项目名称，支持模糊查询") @RequestParam(value = "projectName", required = false) String projectName,
            @ApiParam(value = "调整类型") @RequestParam(value = "type", required = false) AdjustType type,
            @ApiParam(value = "预算金额起始值，区间查询") @RequestParam(value = "amountBegin", required = false) Double amountBegin,
            @ApiParam(value = "预算金额结束值，区间查询") @RequestParam(value = "amountEnd", required = false) Double amountEnd
    ) throws ParseException {
        return indexAdjustmentService.findOnePage(page, size, sort, year, projectCode, projectName, type, amountBegin, amountEnd);
    }

    @ApiOperation(value = "根据ID查询指标（预算）调整")
    @PreAuthorize("hasAnyAuthority('INDEX_ADJUSTMENT_RETRIEVE')")
    @GetMapping(value = "{id}")
    @Json(type = OrganizationDTO.class, filter = "children")
    public IndexAdjustmentDTO findById(
            @ApiParam(value = "支出预算Id", required = true) @PathVariable(name = "id") String id
    ) {
        return indexAdjustmentService.findById(id);
    }

    @ApiOperation(value = "新建指标（预算）调整")
    @PreAuthorize("hasAnyAuthority('INDEX_ADJUSTMENT_CREATE')")
    @PostMapping(value = "")
    public IndexAdjustmentDTO create(
            @ApiParam(value = "指标（预算）调整编辑信息", required = true) @RequestBody @Validated IndexAdjustmentCreateInfoDTO createInfo
    ) throws IOException {
        return indexAdjustmentService.create(createInfo);
    }

    @ApiOperation(value = "提交指标（预算）调整")
    @PreAuthorize("hasAnyAuthority('INDEX_ADJUSTMENT_UPDATE','INDEX_ADJUSTMENT_AUDIT')")
    @PutMapping(value = "/{id}")
    public IndexAdjustmentDTO complete(
            @ApiParam(value = "指标（预算）调整Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "指标（预算）调整编辑信息", required = true) @RequestBody @Validated IndexAdjustmentEditInfoDTO editInfo
    ) throws IOException {
        return indexAdjustmentService.complete(id, editInfo);
    }

    @ApiOperation(value = "删除指标（预算）调整")
    @PreAuthorize("hasAnyAuthority('INDEX_ADJUSTMENT_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "支出预算Id", required = true) @PathVariable(name = "id") String id
    ) throws IOException {
        indexAdjustmentService.delete(id);
    }

    @ApiOperation(value = "检查可用性")
    @PreAuthorize("hasAnyAuthority('INDEX_ADJUSTMENT_RETRIEVE')")
    @GetMapping(value = "/availability")
    public SimpleDataDTO<Boolean> checkAvailability(
            @ApiParam(value = "当前指标（预算）调整ID") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "指标ID", required = true) @RequestParam(value = "indexId") String indexId,
            @ApiParam(value = "金额", required = true) @RequestParam(value = "amount") Double amount
    ) {
        Boolean available = indexAdjustmentService.checkAvailability(id, indexId, amount);
        return new SimpleDataDTO<>(available);
    }
}
