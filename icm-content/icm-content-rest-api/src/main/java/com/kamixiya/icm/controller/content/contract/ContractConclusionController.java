package com.kamixiya.icm.controller.content.contract;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.content.base.PageQueryOptionDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionCreateInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionEditInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionQueryOptionDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.service.content.service.contract.ContractConclusionService;
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
 * ContractConclusionController
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Api(tags = {"合同订立管理"})
@RestController
@RequestMapping("/api/icm-contract/conclusions")
@Validated
public class ContractConclusionController {

    private final ContractConclusionService contractConclusionService;

    @Autowired
    public ContractConclusionController(ContractConclusionService contractConclusionService) {
        this.contractConclusionService = contractConclusionService;
    }

    @ApiOperation(value = "分页查询合同订立信息")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('CONTRACT_CONCLUSION_RETRIEVE')")
    @Json(type = OrganizationDTO.class, filter = "children")
    public PageDataDTO<ContractConclusionDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "id,desc") String sort,
            @ApiParam(value = "状态类型,用于区分申请列表、待办、已办", required = true) @RequestParam("stateType") StateType stateType,
            @ApiParam(value = "是否重大合同") @RequestParam(value = "important", required = false) Boolean important,
            @ApiParam(value = "合同名称,支持模糊查询") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "合同年度") @RequestParam(value = "year", required = false) String year,
            @ApiParam(value = "是否关联采购") @RequestParam(value = "purchase", required = false) Boolean purchase,
            @ApiParam(value = "申请时间，起始日期，区间查询", example = "2019-10-15") @RequestParam(value = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate,
            @ApiParam(value = "申请时间，结束日期，区间查询", example = "2019-10-15") @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @ApiParam(value = "申请单位") @RequestParam(value = "unitId", required = false) String unitId,
            @ApiParam(value = "申请部门") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "申请人名称，支持模糊查询") @RequestParam(value = "declarerName", required = false) String declarerName
    ) {
        return contractConclusionService.findOnePage(
                new ContractConclusionQueryOptionDTO(
                        new PageQueryOptionDTO(page, size, sort),
                        stateType,
                        important,
                        name,
                        year,
                        purchase,
                        beginDate,
                        endDate,
                        unitId,
                        departmentId,
                        declarerName,
                        null
                )
        );
    }

    @ApiOperation(value = "查询全部合同订立信息")
    @GetMapping(value = "/all")
    @Json(type = OrganizationDTO.class, filter = "children")
    public List<ContractConclusionDTO> findAll(
            @ApiParam(value = "状态类型,用于区分申请列表、待办、已办", required = true) @RequestParam("stateType") StateType stateType,
            @ApiParam(value = "是否重大合同") @RequestParam(value = "important", required = false) Boolean important,
            @ApiParam(value = "合同名称,支持模糊查询") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "合同年度") @RequestParam(value = "year", required = false) String year,
            @ApiParam(value = "是否关联采购") @RequestParam(value = "purchase", required = false) Boolean purchase,
            @ApiParam(value = "申请时间，起始日期，区间查询", example = "2019-10-15") @RequestParam(value = "beginDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate,
            @ApiParam(value = "申请时间，结束日期，区间查询", example = "2019-10-15") @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @ApiParam(value = "申请单位") @RequestParam(value = "unitId", required = false) String unitId,
            @ApiParam(value = "申请部门") @RequestParam(value = "departmentId", required = false) String departmentId,
            @ApiParam(value = "申请人名称，支持模糊查询") @RequestParam(value = "declarerName", required = false) String declarerName,
            @ApiParam(value = "合同订立ID,用于在查询到已备案的合同信息") @RequestParam(value = "conclusionId", required = false) String conclusionId
    ) {
        return contractConclusionService.findAll(
                new ContractConclusionQueryOptionDTO(
                        null,
                        stateType,
                        important,
                        name,
                        year,
                        purchase,
                        beginDate,
                        endDate,
                        unitId,
                        departmentId,
                        declarerName,
                        conclusionId
                )
        );
    }

    @ApiOperation(value = "根据ID查询合同订立")
    @PreAuthorize("hasAnyAuthority('CONTRACT_CONCLUSION_RETRIEVE')")
    @GetMapping(value = "/{id}")
    @Json(type = OrganizationDTO.class, filter = "children")
    public ContractConclusionDTO findById(
            @ApiParam(value = "合同订立Id", required = true) @PathVariable(name = "id") String id
    ) {
        return contractConclusionService.findById(id);
    }

    @ApiOperation(value = "新建合同订立")
    @PreAuthorize("hasAnyAuthority('CONTRACT_CONCLUSION_CREATE')")
    @PostMapping(value = "")
    @Json(type = OrganizationDTO.class, filter = "children")
    public ContractConclusionDTO create(
            @ApiParam(value = "合同订立创建信息", required = true) @RequestBody @Validated ContractConclusionCreateInfoDTO createInfo
    ) throws IOException {
        return contractConclusionService.
                create(createInfo);
    }

    @ApiOperation(value = "提交合同订立")
    @PreAuthorize("hasAnyAuthority('CONTRACT_CONCLUSION_UPDATE', 'CONTRACT_CONCLUSION_AUDIT')")
    @PutMapping(value = "/{id}")
    @Json(type = OrganizationDTO.class, filter = "children")
    public ContractConclusionDTO complete(
            @ApiParam(value = "合同订立Id", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "合同订立编辑信息", required = true) @RequestBody @Validated ContractConclusionEditInfoDTO editInfo
    ) throws IOException {
        return contractConclusionService.complete(id, editInfo);
    }

    @ApiOperation(value = "删除合同订立")
    @PreAuthorize("hasAnyAuthority('CONTRACT_CONCLUSION_DELETE')")
    @DeleteMapping(value = "/{id}")
    @Json(type = OrganizationDTO.class, filter = "children")
    public void delete(
            @ApiParam(value = "合同变更Id", required = true) @PathVariable(name = "id") String id
    ) throws IOException {
        contractConclusionService.delete(id);
    }

    @ApiOperation(value = "获取可用金额")
    @GetMapping(value = "/available")
    public Map<String, Double> getAvailable(
            @ApiParam(value = "合同订立ID") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "指标ID列表", required = true) @RequestParam(value = "indexIds") List<String> indexIds,
            @ApiParam(value = "是否关联采购", required = true) @RequestParam(value = "isPurchase") Boolean isPurchase,
            @ApiParam(value = "采购申请ID") @RequestParam(value = "purchaseReportId", required = false) String purchaseReportId
    ) {
        return contractConclusionService.getAvailable(id, indexIds, isPurchase, purchaseReportId);
    }

    @ApiOperation(value = "获取采购可用金额")
    @GetMapping(value = "/purchaseAvailable")
    public SimpleDataDTO<Double> getPurchaseAvailable(
            @ApiParam(value = "合同订立ID") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "采购申请ID", required = true) @RequestParam(value = "purchaseReportId") String purchaseReportId
    ) {
        return new SimpleDataDTO<>(contractConclusionService.getPurchaseAvailable(id, purchaseReportId));
    }

    @ApiOperation(value = "获取可用合同编号")
    @GetMapping(value = "/codeAvailable")
    public SimpleDataDTO<Boolean> getContractNoAvailable(
            @ApiParam(value = "合同订立ID") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "合同编号", required = true) @RequestParam(value = "contractNo") String contractNo
    ) {
        return new SimpleDataDTO<>(contractConclusionService.getContractNoAvailable(id, contractNo));
    }

}