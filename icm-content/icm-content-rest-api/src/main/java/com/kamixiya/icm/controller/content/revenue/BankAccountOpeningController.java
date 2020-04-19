package com.kamixiya.icm.controller.content.revenue;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningCreateInfoDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningEditInfoDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningQueryOptionDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.content.service.revenue.BankAccountOpeningService;
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

/**
 * BankAccountOpeningController
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Api(tags = {"银行账户开户申请管理"})
@RestController
@RequestMapping("/api/icm-revenue/bank/account/opening")
@Validated
public class BankAccountOpeningController {
    private static final String DATA_TYPE = "银行账户开户申请管理";

    private final BankAccountOpeningService bankAccountOpeningService;

    @Autowired
    public BankAccountOpeningController(BankAccountOpeningService bankAccountOpeningService) {
        this.bankAccountOpeningService = bankAccountOpeningService;
    }

    @ApiOperation(value = "分页银行账户开户申请")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_OPENING_RETRIEVE')")
    public PageDataDTO<BankAccountOpeningDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "状态类型,查询类型", required = true) @RequestParam("stateType") StateType stateType,
            @ApiParam(value = "申请单位,支持模糊查询") @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(value = "申请部门,支持模糊查询") @RequestParam(value = "departmentName", required = false) String departmentName,
            @ApiParam(value = "申请人,支持模糊查询") @RequestParam(value = "declarerName", required = false) String declarerName,
            @ApiParam(value = "账户性质") @RequestParam(value = "accountProperty", required = false) String accountProperty,
            @ApiParam(value = "开户银行") @RequestParam(value = "nameOfBank", required = false) String nameOfBank,
            @ApiParam(value = "开户银行全称") @RequestParam(value = "fullNameOfBank", required = false) String fullNameOfBank
    ) {
        return bankAccountOpeningService.findOnePage(new BankAccountOpeningQueryOptionDTO(new PageQueryOptionDTO(page, size, sort), stateType, unitName, departmentName, declarerName, accountProperty, nameOfBank, fullNameOfBank));
    }

    @ApiOperation(value = "根据ID查询银行账户开户申请")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_OPENING_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public BankAccountOpeningDTO findById(
            @ApiParam(value = "银行账户开户申请Id", required = true) @PathVariable(name = "id") String id
    ) {
        return bankAccountOpeningService.findById(id);
    }

    @ApiOperation(value = "创建银行账户开户申请")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_OPENING_CREATE')")
    @PostMapping(value = "")
    public BankAccountOpeningDTO create(
            @ApiParam(value = "银行账户开户申请创建信息", required = true) @RequestBody @Validated BankAccountOpeningCreateInfoDTO createInfo
    ) throws IOException {
        return bankAccountOpeningService.create(createInfo);
    }

    @ApiOperation(value = "提交银行账户开户申请")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_OPENING_UPDATE', 'BANK_ACCOUNT_OPENING_AUDIT')")
    @PutMapping(value = "/{id}")
    public BankAccountOpeningDTO complete(
            @ApiParam(value = "银行账户开户申请Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "银行账户开户申请编辑信息", required = true) @RequestBody @Validated BankAccountOpeningEditInfoDTO editInfo
    ) throws IOException {
        return bankAccountOpeningService.complete(id, editInfo);
    }

    @ApiOperation(value = "删除银行账户开户申请")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_OPENING_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "银行账户开户申请Id", required = true) @PathVariable(name = "id") String id
    ) throws IOException {
        bankAccountOpeningService.delete(id);
    }
}
