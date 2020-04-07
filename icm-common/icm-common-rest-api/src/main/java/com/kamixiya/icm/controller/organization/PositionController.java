package com.kamixiya.icm.controller.organization;

import com.kamixiya.icm.core.json.Json;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.model.organization.position.PositionEditInfoDTO;
import com.kamixiya.icm.service.common.service.organization.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * PositionController
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"岗位管理"})
@RestController
@RequestMapping("/api/organization/positions")
@Validated
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @ApiOperation(value = "通过ID查询岗位")
    @PreAuthorize("hasAnyAuthority('POSITION_RETRIEVE')")
    @GetMapping(value = "/{id}")
    public PositionDTO findById(
            @ApiParam(value = "岗位ID", required = true) @PathVariable(name = "id") String id) {
        return positionService.findById(id);
    }

    @ApiOperation(value = "新建岗位")
    @PreAuthorize("hasAnyAuthority('POSITION_CREATE')")
    @Json(type = OrganizationDTO.class, filter = "children")
    @PostMapping(value = "")
    public OrganizationDTO create(
            @ApiParam(value = "岗位信息", required = true) @RequestBody @Validated PositionEditInfoDTO positionEditInfoDTO) {
        return positionService.create(positionEditInfoDTO);
    }

    @ApiOperation(value = "修改岗位")
    @PreAuthorize("hasAnyAuthority('POSITION_UPDATE')")
    @PutMapping(value = "/{id}")
    public PositionDTO update(
            @ApiParam(value = "岗位ID", required = true) @PathVariable(name = "id") String id,
            @ApiParam(value = "岗位信息", required = true) @RequestBody @Validated PositionEditInfoDTO positionEditInfoDTO) {
        return positionService.update(id, positionEditInfoDTO);
    }

    @ApiOperation(value = "删除岗位")
    @PreAuthorize("hasAnyAuthority('POSITION_DELETE')")
    @DeleteMapping(value = "/{id}")
    public void delete(
            @ApiParam(value = "岗位ID", required = true) @PathVariable(name = "id") String id) {
        positionService.delete(id);
    }

}

