package com.kamixiya.icm.controller.organization;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.unit.UnitDTO;
import com.kamixiya.icm.model.organization.unit.UnitEditInfoDTO;
import com.kamixiya.icm.service.common.service.organization.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * UnitController
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"单位管理"})
@RestController
@RequestMapping("/api/organization/units")
@Validated
public class UnitController {

    private final UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @ApiOperation(value = "通过ID查询单位")
    @PreAuthorize("hasAnyAuthority('UNIT_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public UnitDTO findById(
            @ApiParam(value = "单位ID", required = true) @PathVariable(name = "id") String id) {
        return unitService.findById(id);
    }

    @ApiOperation(value = "新建单位")
    @PreAuthorize("hasAnyAuthority('UNIT_CREATE')")
    @Json(type = OrganizationDTO.class, filter = "children")
    @PostMapping(value = "")
    public OrganizationDTO create(
            @ApiParam(value = "单位信息", required = true) @RequestBody @Validated UnitEditInfoDTO unitEditInfoDTO) {
        return unitService.create(unitEditInfoDTO);
    }

    @ApiOperation(value = "修改单位")
    @PreAuthorize("hasAnyAuthority('UNIT_UPDATE')")
    @PutMapping(value = "/{id}")
    public UnitDTO update(
            @ApiParam(value = "单位ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "单位信息", required = true) @RequestBody @Validated UnitEditInfoDTO unitEditInfoDTO) {
        return unitService.update(id, unitEditInfoDTO);
    }

    @ApiOperation(value = "删除单位")
    @PreAuthorize("hasAnyAuthority('UNIT_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "单位ID", required = true) @PathVariable(name = "id") String id) {
        unitService.delete(id);
    }

    @ApiOperation(value = "检查单位编码唯一性")
    @PreAuthorize("hasAnyAuthority('UNIT_RETRIEVE')")
    @GetMapping(value = "{unitNumber}/only")
    public SimpleDataDTO<Boolean> checkUnitNumber(
            @ApiParam(value = "单位ID, 新增单位时检查账号可用性可不填") @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "单位编码", required = true) @PathVariable(value = "unitNumber") String unitNumber) {
        return unitService.checkUnitNumber(id, unitNumber);
    }
}