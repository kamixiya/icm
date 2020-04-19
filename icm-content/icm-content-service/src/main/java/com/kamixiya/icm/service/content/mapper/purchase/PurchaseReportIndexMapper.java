package com.kamixiya.icm.service.content.mapper.purchase;

import com.kamixiya.icm.model.content.purchase.PurchaseReportIndexDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportIndexEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportDetailIndex;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportIndex;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 采购申请——指标详细信息
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseReportIndexMapper {

    /**
     * 实体转DTO
     *
     * @param entity  实体信息
     * @return DTO
     */
    @Simple
    PurchaseReportIndexDTO toDTO(PurchaseReportIndex entity);

    /**
     * 实体集合转DTO集合
     *
     * @param list 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PurchaseReportIndexDTO> toList(List<PurchaseReportIndex> list);

    /**
     * 编辑信息转实体
     *
     * @param editDto 编辑信息
     * @return 实体
     */
    @Simple
    PurchaseReportIndex toEntity(PurchaseReportIndexEditInfoDTO editDto);



    /**
     * 将品目指标信息实体集合转换为dto集合
     * @param list  实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PurchaseReportIndex> toIndexList(List<PurchaseReportDetailIndex> list);

}
