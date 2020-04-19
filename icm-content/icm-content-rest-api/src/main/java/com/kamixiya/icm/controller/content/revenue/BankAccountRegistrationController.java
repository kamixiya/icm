package com.kamixiya.icm.controller.content.revenue;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationCreateInfoDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationEditInfoDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationQueryOptionDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.content.service.revenue.BankAccountRegistrationService;
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

/**
 * BankAccountRegistrationController
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Api(tags = {"银行账户登记管理"})
@RestController
@RequestMapping("/api/icm-revenue/bank/account/registration")
@Validated
public class BankAccountRegistrationController {
    private static final String DATA_TYPE = "银行账户登记管理";

    private final BankAccountRegistrationService bankAccountRegistrationService;

    @Autowired
    public BankAccountRegistrationController(BankAccountRegistrationService bankAccountRegistrationService) {
        this.bankAccountRegistrationService = bankAccountRegistrationService;
    }

    @ApiOperation(value = "分页银行账户登记信息")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_REGISTRATION_RETRIEVE')")
    public PageDataDTO<BankAccountRegistrationDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "状态类型,查询类型", required = true) @RequestParam("stateType") StateType stateType,
            @ApiParam(value = "登记单位,支持模糊查询") @RequestParam(value = "unitName", required = false) String unitName,
            @ApiParam(value = "登记部门,支持模糊查询") @RequestParam(value = "departmentName", required = false) String departmentName,
            @ApiParam(value = "登记人,支持模糊查询") @RequestParam(value = "registerName", required = false) String registerName,
            @ApiParam(value = "登记时间(开始时间)") @RequestParam(value = "beginTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginTime,
            @ApiParam(value = "登记时间(结束时间)") @RequestParam(value = "endTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endTime,
            @ApiParam(value = "账户名称,支持模糊查询") @RequestParam(value = "accountName", required = false) String accountName,
            @ApiParam(value = "账号") @RequestParam(value = "account", required = false) String account
    ) {
        return bankAccountRegistrationService.findOnePage(new BankAccountRegistrationQueryOptionDTO(new PageQueryOptionDTO(page, size, sort), stateType, unitName, departmentName, registerName, beginTime, endTime, accountName, account));
    }

    @ApiOperation(value = "根据ID查询银行账户登记")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_REGISTRATION_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public BankAccountRegistrationDTO findById(
            @ApiParam(value = "银行账户登记Id", required = true) @PathVariable(name = "id") String id
    ) {
        return bankAccountRegistrationService.findById(id);
    }

    @ApiOperation(value = "创建银行账户登记")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_REGISTRATION_CREATE')")
    @PostMapping(value = "")
    public BankAccountRegistrationDTO create(
            @ApiParam(value = "银行账户登记创建信息", required = true) @RequestBody @Validated BankAccountRegistrationCreateInfoDTO createInfo
    ) throws IOException {
        return bankAccountRegistrationService.create(createInfo);
    }

    @ApiOperation(value = "提交银行账户登记")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_REGISTRATION_UPDATE', 'BANK_ACCOUNT_REGISTRATION_AUDIT')")
    @PutMapping(value = "/{id}")
    public BankAccountRegistrationDTO complete(
            @ApiParam(value = "银行账户登记Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "银行账户登记编辑信息", required = true) @RequestBody @Validated BankAccountRegistrationEditInfoDTO editInfo
    ) throws IOException {
        return bankAccountRegistrationService.complete(id, editInfo);
    }

    @ApiOperation(value = "删除银行账户登记")
    @PreAuthorize("hasAnyAuthority('BANK_ACCOUNT_REGISTRATION_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "银行账户登记Id", required = true) @PathVariable(name = "id") String id
    ) throws IOException {
        bankAccountRegistrationService.delete(id);
    }
}
