package com.kamixiya.icm.service.content.service.payment;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.payment.*;
import com.kamixiya.icm.persistence.content.entity.base.StateType;
import com.kamixiya.icm.persistence.content.entity.payment.ExpenseType;

import java.io.IOException;
import java.util.List;

/**
 * 事前资金申请服务
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public interface PaymentReportService {
    /**
     * 分页查询事前资金申请信息
     *
     * @param paymentReportQueryOption 事前资金申请查询信息
     * @return 分页的事前资金申请详细信息
     */
    PageDataDTO<PaymentReportDTO> findOnePage(PaymentReportQueryOptionDTO paymentReportQueryOption);

    /**
     * 根据ID查询事前资金申请
     *
     * @param id       事前资金申请Id
     * @return 事前资金申请详细信息
     */
    PaymentReportDTO findById(String id);

    /**
     * 新建事前资金申请
     *
     * @param createInfo 事前资金申请创建信息
     * @return 事前资金申请详细信息
     */
    PaymentReportDTO create(PaymentReportCreateInfoDTO createInfo);

    /**
     * 提交事前资金申请
     *
     * @param id       事前资金申请Id
     * @param editInfo 事前资金申请编辑信息
     * @return 事前资金申请详细信息
     */
    PaymentReportDTO complete(String id, PaymentReportEditInfoDTO editInfo);

    /**
     * 删除事前资金申请
     *
     * @param id 事前资金申请Id
     */
    void delete(String id);

    /**
     * 获取可用金额
     *
     * @param id         事前资金申请Id
     * @param contractId 合同备案ID
     * @param purchaseId 会议费采购申请ID
     * @param purchaseId2 非会议费采购申请ID
     * @param indexIds   指标ID列表
     * @return 事前资金申请----可用余额
     */
    PaymentReportAvailableAmountDTO getAvailable(String id, String contractId, String purchaseId, String purchaseId2, List<String> indexIds);

    /**
     * 释放事前资金申请
     *
     * @param ids 事前资金申请ID列表
     * @return 事前资金申请详细信息列表
     */
    List<PaymentReportDTO> release(List<String> ids);

}
