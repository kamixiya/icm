package com.kamixiya.icm.controller.authority;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.security.authority.AuthorityDTO;
import com.kamixiya.icm.model.security.authority.AuthorityEditInfoDTO;
import com.kamixiya.icm.service.common.service.security.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 权限控制器类
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
@Api(tags = {"权限管理"})
@RestController
@RequestMapping("/api/authorities")
@Validated
public class AuthorityController {

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @ApiOperation(value = "根据ID查找权限")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_RETRIEVE')")
    public AuthorityDTO findById(
            @ApiParam(value = "权限ID", required = true) @PathVariable String id) {
        return authorityService.findById(id);
    }

    @ApiOperation(value = "查找权限")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_RETRIEVE')")
    public PageDataDTO<AuthorityDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", defaultValue = "0") @RequestParam(value = "page", required = false) @Min(0) Integer page,
            @ApiParam(value = "每页纪录条数", defaultValue = "20") @RequestParam(value = "size", required = false) @Min(1) @Max(100) Integer size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false) String sort,
            @ApiParam(value = "权限所属的类型") @RequestParam(value = "type", required = false) String type,
            @ApiParam(value = "权限名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "权限编号") @RequestParam(value = "code", required = false) String code,
            @ApiParam(value = "true就是所有,false是当前用户") @RequestParam(value = "isAll", required = false, defaultValue = "true") Boolean isAll) {
        return authorityService.findOnePage(page, size, sort, type, name, code, isAll);
    }

    @ApiOperation(value = "查找所有权限")
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_RETRIEVE')")
    public List<AuthorityDTO> findAll() {
        return authorityService.findAll();
    }

    @ApiOperation(value = "检查编号是否可用")
    @GetMapping(value = "/availability")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_CREATE', 'AUTHORITY_UPDATE')")
    public SimpleDataDTO<Boolean> checkAvailability
            (@ApiParam(value = "权限编号", required = true) @RequestParam("code") @Validated @NotNull String code,
             @ApiParam(value = "权限ID,新建权限时不需要") @RequestParam(value = "id", required = false) String id) {
        SimpleDataDTO<Boolean> simpleDataDTO = new SimpleDataDTO<>();
        simpleDataDTO.setData(authorityService.checkAvailability(code, id));
        return simpleDataDTO;
    }

    @ApiOperation(value = "类型名称下拉框")
    @GetMapping(value = "/types")
    public Set<String> checkAvailability(@ApiParam(value = "权限所属的类型") @RequestParam(value = "type", required = false) String type) {
        return authorityService.getAuthorityTypes(type);
    }

    @ApiOperation(value = "创建权限")
    @PostMapping(value = "")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_CREATE')")
    public AuthorityDTO create(@ApiParam(value = "权限信息", required = true) @RequestBody @Validated AuthorityEditInfoDTO authorityEditInfoDTO) {
        return authorityService.create(authorityEditInfoDTO);
    }

    @ApiOperation(value = "更新权限")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_UPDATE')")
    public AuthorityDTO update(@ApiParam(value = "权限ID", required = true) @PathVariable String id,
                               @ApiParam(value = "权限信息", required = true) @RequestBody @Validated AuthorityEditInfoDTO authorityEditInfoDTO) {
        return authorityService.update(id, authorityEditInfoDTO);
    }

    @ApiOperation(value = "删除权限")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_DELETE')")
    public void delete(@ApiParam(value = "权限ID", required = true) @PathVariable String id) {
        authorityService.delete(id);
    }

    @ApiOperation(value = "分配权限给角色")
    @PutMapping(value = "/{id}/roles")
    @PreAuthorize("hasAnyAuthority('AUTHORITY_UPDATE')")
    public void grantRoles(@ApiParam(value = "权限ID", required = true) @PathVariable String id,
                           @ApiParam(value = "角色ID数组", required = true) @NotNull @RequestBody SimpleDataDTO<String[]> roles) {
        authorityService.grantRoles(id, roles.getData());
    }


}
