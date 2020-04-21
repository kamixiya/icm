package com.kamixiya.icm.controller.content.payment;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.model.content.payment.*;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.payment.ExpenseType;
import com.kamixiya.icm.service.content.service.payment.PaymentReportService;
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

/**
 * PaymentReportController
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Api(tags = {"事前资金申请管理"})
@RestController
@RequestMapping("/api/icm-payment/reports")
@Validated
public class PaymentReportController {

    private final PaymentReportService paymentReportService;

    @Autowired
    public PaymentReportController(PaymentReportService paymentReportService) {
        this.paymentReportService = paymentReportService;
    }

    @ApiOperation(value = "分页查询事前资金申请信息")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('PAYMENT_REPORT_RETRIEVE')")
    public PageDataDTO<PaymentReportDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "状态类型,查询类型", required = true) @RequestParam(value = "stateType", required = false, defaultValue = "UNDONE") StateType stateType,
            @ApiParam(value = "申请单位ID") @RequestParam(value = "unitId", required = false) String unitId,
            @ApiParam(value = "申请部门ID") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "申请人姓名，支持模糊查询") @RequestParam(value = "declarerName", required = false) String declarerName,
            @ApiParam(value = "标题,支持模糊查询") @RequestParam(value = "title", required = false) String title,
            @ApiParam(value = "借款起始金额，区间查询") @RequestParam(value = "beginAmount", required = false) Double beginAmount,
            @ApiParam(value = "借款结束金额，区间查询") @RequestParam(value = "endAmount", required = false) Double endAmount,
            @ApiParam(value = "申请起始日期，区间查询", example = "2019-10-15") @RequestParam(value = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate,
            @ApiParam(value = "申请结束日期，区间查询", example = "2019-10-15") @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @ApiParam(value = "费用类型") @RequestParam(value = "expenseType", required = false) ExpenseType expenseType,
            @ApiParam(value = "采购申请单单号") @RequestParam(value = "code", required = false) String code
    ) {
        return paymentReportService.findOnePage(
                new PaymentReportQueryOptionDTO(
                        new PageQueryOptionDTO(page, size, sort),
                        stateType,
                        unitId,
                        departmentId,
                        declarerName,
                        title,
                        beginAmount,
                        endAmount,
                        beginDate,
                        endDate,
                        expenseType,
                        code,
                        null
                )
        );
    }

    @ApiOperation(value = "根据ID查询事前资金申请")
    @PreAuthorize("hasAnyAuthority('PAYMENT_REPORT_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public PaymentReportDTO findById(
            @ApiParam(value = "事前资金申请Id", required = true) @PathVariable(name = "id") String id
    ) {
        return paymentReportService.findById(id);
    }


    @ApiOperation(value = "新建事前资金申请")
    @PreAuthorize("hasAnyAuthority('PAYMENT_REPORT_CREATE')")
    @PostMapping(value = "")
    public PaymentReportDTO create(
            @ApiParam(value = "事前资金申请创建信息", required = true) @RequestBody @Validated PaymentReportCreateInfoDTO createInfo
    ) throws IOException {
        return paymentReportService.create(createInfo);
    }

    @ApiOperation(value = "提交事前资金申请")
    @PreAuthorize("hasAnyAuthority('PAYMENT_REPORT_UPDATE', 'PAYMENT_REPORT_AUDIT')")
    @PutMapping(value = "/{id}")
    public PaymentReportDTO complete(
            @ApiParam(value = "事前资金申请Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "事前资金申请编辑信息", required = true) @RequestBody @Validated PaymentReportEditInfoDTO editInfo
    ) throws IOException {
        return paymentReportService.complete(id, editInfo);
    }

    @ApiOperation(value = "删除事前资金申请")
    @PreAuthorize("hasAnyAuthority('PAYMENT_REPORT_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "支出预算Id", required = true) @PathVariable(name = "id") String id
    ) throws IOException {
        paymentReportService.delete(id);
    }

    @ApiOperation(value = "获取可用金额")
    @GetMapping(value = "/available")
    public PaymentReportAvailableAmountDTO getAvailable(
            @ApiParam(value = "事前资金申请ID") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "合同备案ID") @RequestParam(value = "contractId", required = false) String contractId,
            @ApiParam(value = "会议费采购申请ID") @RequestParam(value = "purchaseId", required = false) String purchaseId,
            @ApiParam(value = "非会议费采购申请ID") @RequestParam(value = "purchaseId2", required = false) String purchaseId2,
            @ApiParam(value = "指标ID列表", required = true) @RequestParam(value = "indexIds") List<String> indexIds
    ) {
        return paymentReportService.getAvailable(id, contractId, purchaseId, purchaseId2, indexIds);
    }

    @ApiOperation(value = "释放事前资金申请")
    @PreAuthorize("hasAnyAuthority('PAYMENT_REPORT_UPDATE')")
    @PutMapping(value = "")
    public List<PaymentReportDTO> release(
            @ApiParam(value = "事前资金申请ID列表", required = true) @RequestBody @Validated List<String> ids
    ) {
        return paymentReportService.release(ids);
    }
}
