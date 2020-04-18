package com.kamixiya.icm.service.content.service.budget;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;

import java.util.List;

/**
 * 指标库服务
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
public interface IndexLibraryService {

    /**
     * 查询指标详细信息
     *
     * @param sort           排序字段, 例如：字段1,asc,字段2,desc
     * @param year           项目名称
     * @param projectName    项目名称
     * @param projectCode    项目编号
     * @param departmentName 部门名称
     * @param departmentId   申报部门ID
     * @param unitName       单位名称
     * @return 分页的指标详细信息
     */
    List<IndexLibraryDTO> findAll(String sort, String year, String projectName, String projectCode, String departmentName, String departmentId, String unitName);

    /**
     * 分页查询指标详细信息
     *
     * @param page        页号，从0开始
     * @param size        每页纪录条数
     * @param sort        排序字段, 例如：字段1,asc,字段2,desc
     * @param projectName 项目名称
     * @param unitId      单位id
     * @param departmentId      部门id
     * @param indexProjectName  预算指标名称
     * @return 分页的指标详细信息
     */
    PageDataDTO<IndexLibraryDTO> findOnePage(int page, int size, String sort, String projectName, String unitId, String departmentId, String indexProjectName);

    /**
     * 根据Id查询指标详细信息
     *
     * @param id 指标Id
     * @return 根据Id查询指标详细信息
     */
    IndexLibraryDTO findById(String id);


    /**
     * 根据指标id获取指标占用金额
     */
    Double getOccupationAmountById(String id);
}