package com.kamixiya.icm.service.content.service.revenue;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningCreateInfoDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningEditInfoDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningQueryOptionDTO;

import java.io.IOException;

/**
 * 银行账户开户申请服务
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public interface BankAccountOpeningService {
    /**
     * 分页查询银行账户开户申请
     *
     * @param bankAccountOpeningQueryOptionDTO 银行账户开户申请条件
     * @return 分页的银行账户开户申请
     */
    PageDataDTO<BankAccountOpeningDTO> findOnePage(BankAccountOpeningQueryOptionDTO bankAccountOpeningQueryOptionDTO);

    /**
     * 根据ID查询银行账户开户申请
     *
     * @param id 银行账户开户申请Id
     * @return 根据ID查询银行账户开户申请
     */
    BankAccountOpeningDTO findById(String id);

    /**
     * 创建银行账户开户申请
     *
     * @param createInfo 银行账户开户申请创建信息
     * @return 银行账户开户申请
     * @throws IOException IO异常
     */
    BankAccountOpeningDTO create(BankAccountOpeningCreateInfoDTO createInfo) throws IOException;

    /**
     * 提交银行账户开户申请
     *
     * @param id       银行账户开户申请Id
     * @param editInfo 银行账户开户申请编辑信息
     * @return 银行账户开户申请
     * @throws IOException IO异常
     */
    BankAccountOpeningDTO complete(String id, BankAccountOpeningEditInfoDTO editInfo) throws IOException;

    /**
     * 删除银行账户开户申请
     *
     * @param id 银行账户开户申请Id
     * @throws IOException IO异常
     */
    void delete(String id) throws IOException;
}
