package com.kamixiya.icm.service.content.mapper.payment;

import com.kamixiya.icm.model.content.payment.PaymentReportLabourExpenseEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.payment.PaymentReportLabourExpense;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 事前资金申请劳务费实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentReportLabourExpenseMapper {

    /**
     * 编辑信息转实体
     *
     * @param dto 编辑信息
     * @return 实体
     */
    @Simple
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "paymentReport", ignore = true)
    PaymentReportLabourExpense toEntity(PaymentReportLabourExpenseEditInfoDTO dto);
}
