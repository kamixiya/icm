package com.kamixiya.icm.service.content.service.budget;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentCreateInfoDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentDTO;
import com.kamixiya.icm.model.content.budget.adjustment.IndexAdjustmentEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.budget.adjustment.AdjustType;

import java.io.IOException;
import java.text.ParseException;

/**
 * 指标（预算）调整服务
 *
 * @author Zhu Jie
 * @date 2020/4/17
 */
public interface IndexAdjustmentService {
    /**
     * 根据ID查询指标（预算）调整
     *
     * @param id       支出预算Id
     * @return 指标（预算）调整详细信息
     */
    IndexAdjustmentDTO findById(String id);

    /**
     * 分页查询指标（预算）调整信息
     *
     * @param page        页号，从0开始
     * @param size        每页纪录条数
     * @param sort        排序字段, 例如：字段1,asc,字段2,desc
     * @param year        年份
     * @param projectCode 项目编号，支持模糊查询
     * @param projectName 项目名称，支持模糊查询
     * @param type        调整类型
     * @param amountBegin 预算金额起始值，区间查询
     * @param amountEnd   预算金额结束值，区间查询
     * @return 分页的指标（预算）调整信息
     * @throws ParseException 解析异常
     */
    PageDataDTO<IndexAdjustmentDTO> findOnePage(int page, int size, String sort, String year, String projectCode, String projectName, AdjustType type, Double amountBegin, Double amountEnd) throws ParseException;


    /**
     * 新建指标（预算）调整
     *
     * @param createInfo 指标（预算）调整编辑信息
     * @return 指标（预算）调整详细信息
     * @throws IOException IO异常
     */
    IndexAdjustmentDTO create(IndexAdjustmentCreateInfoDTO createInfo) throws IOException;

    /**
     * 提交指标（预算）调整
     *
     * @param id       指标（预算）调整Id
     * @param editInfo 指标（预算）调整编辑信息
     * @return 指标（预算）调整详细信息
     * @throws IOException IO异常
     */
    IndexAdjustmentDTO complete(String id, IndexAdjustmentEditInfoDTO editInfo) throws IOException;

    /**
     * 删除指标（预算）调整
     *
     * @param id 指标（预算）调整Id
     * @throws IOException IO异常
     */
    void delete(String id) throws IOException;

    /**
     * 检查可用性
     *
     *
     * @param id 当前指标（预算）调整ID
     * @param indexId 指标ID
     * @param amount  金额
     * @return 是否可用
     */
    Boolean checkAvailability(String id, String indexId, Double amount);
}
