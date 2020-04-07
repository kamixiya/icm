package com.kamixiya.icm.service.common.service.organization;

import com.kamixiya.icm.model.common.SimpleDataDTO;
import com.kamixiya.icm.model.organization.organization.OrganizationDTO;
import com.kamixiya.icm.model.organization.unit.UnitDTO;
import com.kamixiya.icm.model.organization.unit.UnitEditInfoDTO;

/**
 * UnitService
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public interface UnitService {

    /**
     * 根据单位id查询单位信息
     *
     * @param id 单位id
     * @return 单位dto信息
     */
    UnitDTO findById(String id);

    /**
     * 创建单位信息
     *
     * @param unitEditInfoDTO 单位信息
     * @return 组织dto信息
     */
    OrganizationDTO create(UnitEditInfoDTO unitEditInfoDTO);

    /**
     * 修改单位信息
     *
     * @param id              单位id
     * @param unitEditInfoDTO 单位信息
     * @return 单位dto信息
     */
    UnitDTO update(String id, UnitEditInfoDTO unitEditInfoDTO);

    /**
     * 删除单位信息
     *
     * @param id 单位id
     */
    void delete(String id);

    /**
     * 检查单位编码是否唯一
     *
     * @param id         单位id
     * @param unitNumber 单位编码
     * @return 是否可用（true可用/false不可用）
     */
    SimpleDataDTO<Boolean> checkUnitNumber(String id, String unitNumber);
}
