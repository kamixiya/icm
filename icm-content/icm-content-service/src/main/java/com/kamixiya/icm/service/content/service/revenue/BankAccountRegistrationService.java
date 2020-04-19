package com.kamixiya.icm.service.content.service.revenue;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationCreateInfoDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationEditInfoDTO;
import com.kamixiya.icm.model.content.revenue.Registration.BankAccountRegistrationQueryOptionDTO;

import java.io.IOException;

/**
 * 银行账户登记服务
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public interface BankAccountRegistrationService {
    /**
     * 分页查询银行账户登记信息
     *
     * @param bankAccountRegistrationQueryOptionDTO 银行账户登记信息查询条件
     * @return 分页的银行账户登记信息
     */
    PageDataDTO<BankAccountRegistrationDTO> findOnePage(BankAccountRegistrationQueryOptionDTO bankAccountRegistrationQueryOptionDTO);

    /**
     * 根据ID查询银行账户登记
     *
     * @param id 银行账户登记Id
     * @return 根据ID查询银行账户登记
     */
    BankAccountRegistrationDTO findById(String id);

    /**
     * 创建银行账户登记
     *
     * @param createInfo 银行账户登记创建信息
     * @return 银行账户登记信息
     * @throws IOException IO异常
     */
    BankAccountRegistrationDTO create(BankAccountRegistrationCreateInfoDTO createInfo) throws IOException;

    /**
     * 提交银行账户登记
     *
     * @param id       银行账户登记Id
     * @param editInfo 银行账户登记编辑信息
     * @return 银行账户登记信息
     * @throws IOException IO异常
     */
    BankAccountRegistrationDTO complete(String id, BankAccountRegistrationEditInfoDTO editInfo) throws IOException;

    /**
     * 删除银行账户登记
     *
     * @param id 银行账户登记Id
     * @throws IOException IO异常
     */
    void delete(String id) throws IOException;
}
