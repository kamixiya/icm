package com.kamixiya.icm.service.content.mapper.payment;

import com.kamixiya.icm.model.content.payment.PaymentReportIndexDTO;
import com.kamixiya.icm.model.content.payment.PaymentReportIndexEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.payment.PaymentReportIndex;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 事前资金申请指标信息实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentReportIndexMapper {

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
    @Mapping(target = "index", ignore = true)
    @Mapping(target = "passageAmount", ignore = true)
    @Mapping(target = "paymentReport", ignore = true)
    @Mapping(target = "releaseAmount", ignore = true)
    PaymentReportIndex toEntity(PaymentReportIndexEditInfoDTO dto);

    /**
     * 编辑信息转实体
     *
     * @param dto 编辑信息
     * @return 实体
     */
    @Simple
    PaymentReportIndex toEntity(PaymentReportIndexDTO dto);

    /**
     * 实体转编辑信息
     *
     * @param adjustmentIndex 实体
     * @return 编辑信息
     */
    @Simple
    @Mapping(target = "indexId", source = "index.id")
    PaymentReportIndexEditInfoDTO toEditDTO(PaymentReportIndex adjustmentIndex);

    /**
     * 实体列表转编辑信息列表
     *
     * @param adjustmentIndexes 实体列表
     * @return 编辑信息列表
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PaymentReportIndexEditInfoDTO> toList(List<PaymentReportIndex> adjustmentIndexes);
}
