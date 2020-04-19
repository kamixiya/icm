package com.kamixiya.icm.controller.content.purchase;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportCreateInfoDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.content.service.purchase.PurchaseReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * PurchaseReportController
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Api(tags = {"采购申请管理"})
@RestController
@RequestMapping("/api/icm-purchase/reports")
@Validated
public class PurchaseReportController {

    private final PurchaseReportService purchaseReportService;

    @Autowired
    public PurchaseReportController(PurchaseReportService purchaseReportService) {
        this.purchaseReportService = purchaseReportService;
    }

    @ApiOperation(value = "分页查询采购申请信息")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_RETRIEVE')")
    public PageDataDTO<PurchaseReportDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "状态类型,查询类型", required = true) @RequestParam(value = "stateType", required = false, defaultValue = "ALL") StateType stateType,
            @ApiParam(value = "申请部门ID") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "申请人姓名，支持模糊查询") @RequestParam(value = "declarerName", required = false) String declarerName,
            @ApiParam(value = "业务标题,支持模糊查询") @RequestParam(value = "title", required = false) String title,
            @ApiParam(value = "申请单位ID") @RequestParam(value = "unitId", required = false) String unitId,
            @ApiParam(value = "采购申请起始日期，区间查询", example = "2019-10-15") @RequestParam(value = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate,
            @ApiParam(value = "采购申请结束日期，区间查询", example = "2019-10-15") @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @ApiParam(value = "采购单号") @RequestParam(value = "code", required = false) String code,
            @ApiParam(value = "最小采购金额") @RequestParam(value = "beginAmount", required = false) Double beginAmount,
            @ApiParam(value = "最大采购金额") @RequestParam(value = "endAmount", required = false) Double endAmount,
            @ApiParam(value = "品目名称id") @RequestParam(value = "categoryId", required = false) String categoryId

    ) {
        return purchaseReportService.findOnePage(page, size, sort, stateType, departmentId, declarerName, title, unitId, beginDate, endDate, beginAmount, endAmount, code, categoryId);
    }

    @ApiOperation(value = "查询当前操作可获取的采购申请")
    @GetMapping(value = "/all/done")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_RETRIEVE')")
    public List<PurchaseReportDTO> findAllDone(
            @ApiParam(value = "采购申请id()") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "部门id()") @RequestParam(value = "departmentId", required = false) String departmentId
    ) {
        return purchaseReportService.findAllDone(id, departmentId);
    }

    @ApiOperation(value = "查询可用余额大于0的采购申请")
    @GetMapping(value = "/available")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_RETRIEVE')")
    public List<PurchaseReportDTO> findAvailable(
            @ApiParam(value = "部门id()", required = true) @RequestParam(value = "departmentId") String departmentId,
            @ApiParam(value = "采购id") @RequestParam(value = "id", required = false) String id

    ) {
        return purchaseReportService.findAvailable(departmentId, id);
    }

    @ApiOperation(value = "根据ID查询采购申请")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public PurchaseReportDTO findById(
            @ApiParam(value = "采购申请Id", required = true) @PathVariable(name = "id") String id
    ) {
        return purchaseReportService.findById(id);
    }

    @ApiOperation(value = "新建采购申请")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_CREATE')")
    @PostMapping(value = "")
    public PurchaseReportDTO create(
            @ApiParam(value = "采购申请创建信息", required = true) @RequestBody @Validated PurchaseReportCreateInfoDTO createInfo
    ) throws IOException {
        return purchaseReportService.create(createInfo);
    }

    @ApiOperation(value = "提交采购申请")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_UPDATE','PURCHASE_REPORT_AUDIT')")
    @PutMapping(value = "/{id}")
    public PurchaseReportDTO complete(
            @ApiParam(value = "采购申请Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "采购申请编辑信息", required = true) @RequestBody @Validated PurchaseReportEditInfoDTO editInfo
    ) throws IOException {
        return purchaseReportService.complete(id, editInfo);
    }

    @ApiOperation(value = "删除采购申请")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "支出预算Id", required = true) @PathVariable(name = "id") String id
    ) {
        purchaseReportService.delete(id);
    }

    @ApiOperation(value = "获取采购品目中所用的指标库的可用余额")
    @PreAuthorize("hasAnyAuthority('PURCHASE_REPORT_DELETE')")
    @GetMapping(value = "/index")
    public Map<String, Double> getIndex(
            @ApiParam(value = "当前指标id集合", required = true) @RequestParam(name = "ids") List<String> ids,
            @ApiParam(value = "采购申请ID") @RequestParam(name = "id", required = false) String id
    ) {
        return purchaseReportService.getIndex(id, ids);
    }
}