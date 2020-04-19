package com.kamixiya.icm.service.content.mapper.purchase;

import com.kamixiya.icm.model.content.purchase.PurchaseReportDetailDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportDetailEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportDetail;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 采购申请——采购内容及资金来源详细信息实体与DTO互转的工具类
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseReportDetailMapper {

    /**
     * 实体转DTO
     *
     * @param entity  实体信息
     * @return DTO
     */
    @Simple
    PurchaseReportDetailDTO toDTO(PurchaseReportDetail entity);

    /**
     * 编辑信息转实体
     *
     * @param editDto 编辑信息
     * @return 实体
     */
    @Simple
    @Mapping(target = "indexes", ignore = true)
    PurchaseReportDetail dtoToEntity(PurchaseReportDetailEditInfoDTO editDto);

    /**
     * 实体集合转DTO集合
     *
     * @param list 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PurchaseReportDetailDTO> toList(List<PurchaseReportDetail> list);
}
