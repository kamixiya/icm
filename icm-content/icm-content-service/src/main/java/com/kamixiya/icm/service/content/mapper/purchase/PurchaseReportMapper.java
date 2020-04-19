package com.kamixiya.icm.service.content.mapper.purchase;

import com.kamixiya.icm.model.content.purchase.PurchaseReportBaseDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportCreateInfoDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportDTO;
import com.kamixiya.icm.model.content.purchase.PurchaseReportEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseReport;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

/**
 * 采购申请详细信息实体与DTO互转的工具类
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseReportMapper {

    /**
     * 实体转DTO
     *
     * @param entity  实体信息
     * @return DTO
     */
    @Simple
    @Mapping(target = "attaches", ignore = true)
    @Mapping(target = "indexes", ignore = true)
    @Mapping(target = "details", ignore = true)
    PurchaseReportDTO toDTO(PurchaseReport entity);

    /**
     * 实体转基础dto
     *
     * @param entity 实体
     * @return 基础dto
     */
    PurchaseReportBaseDTO toBaseDTO(PurchaseReport entity);

    /**
     * 实体集合转DTO集合
     *
     * @param list    实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PurchaseReportDTO> toList(Collection<PurchaseReport> list);

    /**
     * 编辑信息转实体
     *
     * @param createInfoDTO 编辑信息
     * @return 实体
     */
    @Simple
    @Mapping(target = "attaches", ignore = true)
    @Mapping(target = "indexes", ignore = true)
    @Mapping(target = "details", ignore = true)
    PurchaseReport toEntity(PurchaseReportCreateInfoDTO createInfoDTO);

    /**
     * 通过编辑信息更新实体
     *
     * @param editInfoDTO 编辑信息
     * @param entity 实体
     * @return 更新后得到实体
     */
    @Simple
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "indexes", ignore = true)
    @Mapping(target = "attaches", ignore = true)
    void updateEntity(PurchaseReportEditInfoDTO editInfoDTO, @MappingTarget PurchaseReport entity);

    /**
     *
     * @param dto 采购申请基本信息
     * @return 采购申请实体
     */
    @Simple
    PurchaseReport toEntity(PurchaseReportBaseDTO dto);


}
