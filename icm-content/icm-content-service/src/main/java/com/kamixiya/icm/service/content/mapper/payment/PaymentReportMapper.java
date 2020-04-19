package com.kamixiya.icm.service.content.mapper.payment;

import com.kamixiya.icm.model.content.payment.PaymentReportCreateInfoDTO;
import com.kamixiya.icm.model.content.payment.PaymentReportDTO;
import com.kamixiya.icm.model.content.payment.PaymentReportEditInfoDTO;
import com.kamixiya.icm.model.content.payment.PaymentReportInfoDTO;
import com.kamixiya.icm.persistence.content.entity.payment.PaymentReport;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.*;

import java.util.List;

/**
 * 事前资金申请实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentReportMapper {

    /**
     * 实体转DTO
     *
     * @param entity 实体
     * @return dto
     */
    @Simple
    @Mapping(target = "contractPurchaseReport", source = "contract.purchaseReport")
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "lastModifyUser", ignore = true)
    PaymentReportDTO toDTO(PaymentReport entity);

    @Named("toSimpleDTO")
    @Mapping(target = "meetings", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "abroadExpenses", ignore = true)
    @Mapping(target = "guests", ignore = true)
    @Mapping(target = "itineraries", ignore = true)
    @Mapping(target = "accommodations", ignore = true)
    @Mapping(target = "guestExpenses", ignore = true)
    @Mapping(target = "travelExpenses", ignore = true)
    @Mapping(target = "labourExpenses", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "paymentDetails", ignore = true)
    @Mapping(target = "attaches", ignore = true)
    @Mapping(target = "meetingIndexes", ignore = true)
    @Mapping(target = "trainingIndexes", ignore = true)
    @Mapping(target = "abroadIndexes", ignore = true)
    @Mapping(target = "officialIndexes", ignore = true)
    @Mapping(target = "travelIndexes", ignore = true)
    @Mapping(target = "serviceIndexes", ignore = true)
    @Mapping(target = "officialCarIndexes", ignore = true)
    @Mapping(target = "contractIndexes", ignore = true)
    @Mapping(target = "purchaseIndexes", ignore = true)
    @Mapping(target = "generalIndexes", ignore = true)
    PaymentReportDTO toSimpleDTO(PaymentReport entity);

    /**
     * 实体集合转DTO集合,忽略所有列表映射
     *
     * @param list 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedByName = "toSimpleDTO")
    List<PaymentReportDTO> toList(List<PaymentReport> list);

    /**
     * 实体集合转DTO集合，包含所有列表映射
     *
     * @param list 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PaymentReportDTO> toAllList(List<PaymentReport> list);

    /**
     * 编辑信息转实体
     *
     * @param createInfo 编辑信息
     * @return 实体
     */
    @Simple
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "declarer", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "purchaseReport", ignore = true)
    @Mapping(target = "purchaseReport2", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "abroadExpenses", ignore = true)
    @Mapping(target = "guests", ignore = true)
    @Mapping(target = "itineraries", ignore = true)
    @Mapping(target = "accommodations", ignore = true)
    @Mapping(target = "guestExpenses", ignore = true)
    @Mapping(target = "travelExpenses", ignore = true)
    @Mapping(target = "labourExpenses", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "paymentDetails", ignore = true)
    @Mapping(target = "attaches", ignore = true)
    @Mapping(target = "meetingIndexes", ignore = true)
    @Mapping(target = "trainingIndexes", ignore = true)
    @Mapping(target = "abroadIndexes", ignore = true)
    @Mapping(target = "officialIndexes", ignore = true)
    @Mapping(target = "travelIndexes", ignore = true)
    @Mapping(target = "serviceIndexes", ignore = true)
    @Mapping(target = "officialCarIndexes", ignore = true)
    @Mapping(target = "contractIndexes", ignore = true)
    @Mapping(target = "purchaseIndexes", ignore = true)
    @Mapping(target = "generalIndexes", ignore = true)
    PaymentReport toEntity(PaymentReportCreateInfoDTO createInfo);

    /**
     * 通过编辑信息更新实体
     *
     * @param entity   实体
     * @param editInfo 编辑信息
     */
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "contract", ignore = true)
    @Mapping(target = "declarer", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "purchaseReport", ignore = true)
    @Mapping(target = "purchaseReport2", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "meetings", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "abroadExpenses", ignore = true)
    @Mapping(target = "guests", ignore = true)
    @Mapping(target = "itineraries", ignore = true)
    @Mapping(target = "accommodations", ignore = true)
    @Mapping(target = "guestExpenses", ignore = true)
    @Mapping(target = "travelExpenses", ignore = true)
    @Mapping(target = "labourExpenses", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "paymentDetails", ignore = true)
    @Mapping(target = "attaches", ignore = true)
    @Mapping(target = "meetingIndexes", ignore = true)
    @Mapping(target = "trainingIndexes", ignore = true)
    @Mapping(target = "abroadIndexes", ignore = true)
    @Mapping(target = "officialIndexes", ignore = true)
    @Mapping(target = "travelIndexes", ignore = true)
    @Mapping(target = "serviceIndexes", ignore = true)
    @Mapping(target = "officialCarIndexes", ignore = true)
    @Mapping(target = "contractIndexes", ignore = true)
    @Mapping(target = "purchaseIndexes", ignore = true)
    @Mapping(target = "generalIndexes", ignore = true)
    void updateEntity(@MappingTarget PaymentReport entity, PaymentReportEditInfoDTO editInfo);

    /**
     * 实体转DTO
     *
     * @param paymentReport 实体
     * @return dto
     */
    @Simple
    @Mapping(target = "contractPurchaseReport", source = "contract.purchaseReport")
    PaymentReportInfoDTO toInfoDTO(PaymentReport paymentReport);
}
