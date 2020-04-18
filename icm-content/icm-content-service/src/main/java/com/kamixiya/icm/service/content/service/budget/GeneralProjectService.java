package com.kamixiya.icm.service.content.service.budget;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectDTO;
import com.kamixiya.icm.model.content.budget.general.GeneralProjectEditInfoDTO;

import java.util.List;

/**
 * GeneralProjectService
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public interface GeneralProjectService {
    /**
     * 分页查询项目库信息
     *
     * @param page                页号，从0开始
     * @param size                每页纪录条数
     * @param sort                排序字段, 例如：字段1,asc,字段2,desc
     * @param departmentId        申报部门
     * @param name                项目名称
     * @param projectType         项目类别
     * @param detailedProjectType 项目类别明细
     * @param code                项目编号
     * @param year                申报年份
     * @return 分页的项目库信息
     */
    PageDataDTO<GeneralProjectDTO> findOnePage(int page, int size, String sort, String departmentId, String name, String projectType, String detailedProjectType, String code, String year);

    /**
     * 新建项目库
     *
     * @param generalProjectCreateInfo 项目库编辑信息
     * @return 项目库详细信息
     */
    GeneralProjectDTO create(GeneralProjectCreateInfoDTO generalProjectCreateInfo);

    /**
     * 提交项目库
     *
     * @param id                     项目Id
     * @param generalProjectEditInfo 项目库编辑信息
     * @return 项目库详细信息
     */
    GeneralProjectDTO complete(String id, GeneralProjectEditInfoDTO generalProjectEditInfo);

    /**
     * 根据ID查询项目库
     *
     * @param id       项目Id
     * @return 项目库详细信息
     */
    GeneralProjectDTO findById(String id);

    /**
     * 删除项目库
     *
     * @param id 项目Id
     */
    void delete(String id);

    /**
     * 查询全部项目库信息
     *
     * @param unitId              申报单位
     * @param departmentId        申报部门
     * @param name                项目名称
     * @param projectType         项目类别
     * @param detailedProjectType 项目类别明细
     */
    List<GeneralProjectDTO> findAll(String unitId, String departmentId, String name, String projectType, String detailedProjectType);
}
