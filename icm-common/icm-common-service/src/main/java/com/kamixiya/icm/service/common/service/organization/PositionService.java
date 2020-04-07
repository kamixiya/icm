package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.position.PositionDTO;
import com.kamixiya.icm.model.organization.position.PositionEditInfoDTO;

/**
 * PositionService
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public interface PositionService {

    /**
     * 根据岗位id查询岗位信息
     *
     * @param id 岗位id
     * @return 岗位dto信息
     */
    PositionDTO findById(String id);

    /**
     * 创建岗位
     *
     * @param positionEditInfoDTO 岗位信息
     * @return 组织dto信息
     */
    OrganizationDTO create(PositionEditInfoDTO positionEditInfoDTO);


    /**
     * 修改岗位信息
     *
     * @param id                  岗位id
     * @param positionEditInfoDTO 岗位信息
     * @return 岗位dto信息
     */
    PositionDTO update(String id, PositionEditInfoDTO positionEditInfoDTO);

    /**
     * 删除岗位
     *
     * @param id 岗位id
     */
    void delete(String id);

}
