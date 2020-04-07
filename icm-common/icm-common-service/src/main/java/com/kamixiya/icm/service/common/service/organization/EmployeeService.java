package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeDTO;
import com.kamixiya.icm.model.organization.employee.EmployeeEditInfoDTO;

import java.util.List;

/**
 * EmployeeService
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public interface EmployeeService {

    /**
     * 根据员工id查询员工信息
     *
     * @param id 员工id
     * @return 员工dto信息
     */
    EmployeeDTO findById(String id);

    /**
     * 创建员工
     *
     * @param employeeEditInfoDTO 员工信息
     * @return 员工dto信息集合
     */
    EmployeeDTO create(EmployeeEditInfoDTO employeeEditInfoDTO);

    /**
     * 修改员工
     *
     * @param id                  员工id
     * @param employeeEditInfoDTO 员工信息
     * @return 员工dto信息集合
     */
    EmployeeDTO update(String id, EmployeeEditInfoDTO employeeEditInfoDTO);

    /**
     * 删除员工
     *
     * @param id 员工id
     */
    void delete(String id);

    /**
     * 员工绑定用户
     *
     * @param employeeId 员工id
     * @param userId     用户id
     * @return 员工dto信息
     */
    EmployeeDTO bindUser(String employeeId, String userId);

    /**
     * 检查员工编号唯一性
     *
     * @param id   员工id
     * @param code 员工编号
     * @return 是否可用（true可用/false不可用）
     */
    SimpleDataDTO<Boolean> checkCode(String id, String code);

    /**
     * 查找所有员工
     *
     * @param name      员工姓名
     * @param available 员工状态
     * @return 员工dto集合
     */
    List<EmployeeDTO> findAll(String sort, String name, Boolean available);
}
