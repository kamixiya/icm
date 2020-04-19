package com.kamixiya.icm.service.content.service.contract;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionCreateInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionEditInfoDTO;
import com.kamixiya.icm.model.content.contract.ContractConclusionQueryOptionDTO;
import com.kamixiya.icm.persistence.content.entity.contract.ContractConclusion;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 合同订立服务
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public interface ContractConclusionService {
    /**
     * 分页查询合同订立信息
     *
     * @param contractConclusionQueryOptionDTO 合同订立查询信息
     * @return 分页的合同订立详细信息列表
     */
    PageDataDTO<ContractConclusionDTO> findOnePage(ContractConclusionQueryOptionDTO contractConclusionQueryOptionDTO);

    /**
     * 查询全部合同订立信息
     *
     * @param contractConclusionQueryOptionDTO 合同订立查询信息
     * @return 合同订立详细信息列表
     */
    List<ContractConclusionDTO> findAll(ContractConclusionQueryOptionDTO contractConclusionQueryOptionDTO);

    /**
     * 根据ID查询合同订立
     *
     * @param id       合同订立Id
     * @return 合同订立详细信息
     */
    ContractConclusionDTO findById(String id);

    /**
     * 新建合同订立
     *
     * @param createInfo 合同订立创建信息
     * @return 合同订立详细信息
     * @throws IOException IO异常
     */
    ContractConclusionDTO create(ContractConclusionCreateInfoDTO createInfo) throws IOException;

    /**
     * 提交合同订立
     *
     * @param id       合同订立Id
     * @param editInfo 合同订立编辑信息
     * @return 合同订立详细信息
     * @throws IOException IO异常
     */
    ContractConclusionDTO complete(String id, ContractConclusionEditInfoDTO editInfo) throws IOException;

    /**
     * 删除合同订立
     *
     * @param id 合同订立Id
     * @throws IOException IO异常
     */
    void delete(String id) throws IOException;

    /**
     * 获取可用金额
     *
     * @param id       合同订立ID
     * @param indexIds 指标ID列表
     * @param isPurchase 是否关联采购
     * @param purchaseReportId 采购申请Id
     * @return map形式的指标和可用余额
     */
    Map<String, Double> getAvailable(String id, List<String> indexIds, Boolean isPurchase, String purchaseReportId);

    /**
     * 获取采购可用金额
     *
     * @param id               合同订立ID
     * @param purchaseReportId 采购申请Id
     * @return 采购可用金额
     */
    Double getPurchaseAvailable(String id, String purchaseReportId);

    /**
     * 检查可用合同编号
     *
     * @param id   合同订立ID
     * @param contractNo 合同编号
     * @return 检查可用合同编号
     */
    Boolean getContractNoAvailable(String id, String contractNo);


    /**
     * 恢复指标金额
     *
     * @param conclusion 合同订立实体
     * @param isRecover 是否立刻回退金额
     * @return 变更金额map, key为采购申请指标ID或指标库ID, value为变化的金额
     */
    Map<Long, Double> recoverAmount(ContractConclusion conclusion, boolean isRecover);

}
