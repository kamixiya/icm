package com.kamixiya.icm.service.content.mapper.purchase;

import com.kamixiya.icm.model.content.purchase.PurchaseReportIndexDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportIndexEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReportDetailIndex;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 采购申请——指标详细实体与DTO互转的工具类

 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseReportDetailIndexMapper {

    /**
     * 编辑信息dto转实体
     *
     * @param dto     编辑信息
     * @return 实体
     */
    @Simple
    PurchaseReportDetailIndex toEntity(PurchaseReportIndexEditInfoDTO dto);

    /**
     * 实体转DTO
     *
     * @param index   实体
     * @return DTO
     */
    @Simple
    @Mapping(target = "index", ignore = true)
    PurchaseReportIndexDTO toDTO(PurchaseReportDetailIndex index);

    /**
     * 实体集合转DTO集合
     *
     * @param indexes 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PurchaseReportIndexDTO> toList(List<PurchaseReportDetailIndex> indexes);
}
