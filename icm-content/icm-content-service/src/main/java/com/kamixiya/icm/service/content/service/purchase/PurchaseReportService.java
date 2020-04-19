package com.kamixiya.icm.service.content.service.purchase;

import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportCreateInfoDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.base.StateType;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购申请服务
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
public interface PurchaseReportService {
    /**
     * 分页查询采购申请信息
     *
     * @param page               页号，从0开始
     * @param size               每页纪录条数
     * @param sort               排序字段, 例如：字段1,asc,字段2,desc
     * @param stateType          状态类型,查询类型
     * @param declarerName       申请部门ID
     * @param declarerId         申请人姓名
     * @param title              业务标题,支持模糊查询
     * @param unitId             申请单位id
     * @param beginDate          开始时间
     * @param endDate            结束时间
     * @param beginAmount        最小采购金额
     * @param endAmount          最大采购金额
     * @param code               编码
     * @param categoryId        品目名称id
     * @return 分页的采购申请详细信息
     */
    PageDataDTO<PurchaseReportDTO> findOnePage(int page, int size, String sort, StateType stateType, String declarerName, String declarerId, String title, String unitId, Date beginDate, Date endDate, Double beginAmount, Double endAmount, String code, String categoryId);

    /**
     * 查询全部采购申请信息
     *
     * @param id   采购申请id
     * @param departmentId  部门id
     * @return 采购申请详细信息列表
     */
    List<PurchaseReportDTO> findAllDone(String id, String departmentId);

    /**
     * 根据ID查询采购申请
     *
     * @param id       采购申请Id
     * @return 采购申请详细信息
     */
    PurchaseReportDTO findById(String id);

    /**
     * 新建采购申请
     *
     * @param createInfo 采购申请创建信息
     * @return 采购申请详细信息
     * @throws IOException io异常
     */
    PurchaseReportDTO create(PurchaseReportCreateInfoDTO createInfo) throws IOException;

    /**
     * 提交采购申请
     *
     * @param id       采购申请Id
     * @param editInfo 采购申请编辑信息
     * @return 采购申请详细信息
     */
    PurchaseReportDTO complete(String id, PurchaseReportEditInfoDTO editInfo) throws IOException;

    /**
     * 删除采购申请
     *
     * @param id 采购申请Id
     */
    void delete(String id);

    /**
     * 获取指标余额
     *
     * @param id  采购申请id
     * @param ids 指标库的id
     * @return 指标余额的map
     */
    Map<String, Double> getIndex(String id, List<String> ids);

    /**
     * 查询可用余额大于0的采购申请
     *
     * @param departmentId  部门id
     * @param id 采购id
     * @return
     */
    List<PurchaseReportDTO> findAvailable(String departmentId, String id);

}
