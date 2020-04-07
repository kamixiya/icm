package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.organization.department.DepartmentDTO;
import com.kamixiya.icm.model.organization.department.DepartmentEditInfoDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;

/**
 * DepartmentService
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public interface DepartmentService {

    /**
     * 根据部门id查询部门信息
     *
     * @param id 部门id
     * @return 部门dto信息
     */
    DepartmentDTO findById(String id);

    /**
     * 创建部门
     *
     * @param departmentEditInfoDTO 部门信息
     * @return 组织dto信息
     */
    OrganizationDTO create(DepartmentEditInfoDTO departmentEditInfoDTO);

    /**
     * 修改部门
     *
     * @param id                    部门id
     * @param departmentEditInfoDTO 部门信息
     * @return 部门dto信息
     */
    DepartmentDTO update(String id, DepartmentEditInfoDTO departmentEditInfoDTO);

    /**
     * 删除部门
     *
     * @param id 部门id
     */
    void delete(String id);

}
