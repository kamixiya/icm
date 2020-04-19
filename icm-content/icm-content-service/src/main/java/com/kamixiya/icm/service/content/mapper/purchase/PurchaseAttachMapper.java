package com.kamixiya.icm.service.content.mapper.purchase;

import com.kamixiya.icm.model.content.purchase.AttachDTO;
import com.kamixiya.icm.model.content.purchase.AttachEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.purchase.PurchaseAttach;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 采购计划附件详细信息实体与DTO互转的工具类
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurchaseAttachMapper {

    /**
     * DTO转实体
     *
     * @param dto     编辑信息
     * @return 实体
     */
    @Simple
    PurchaseAttach toEntity(AttachEditInfoDTO dto);

    /**
     * 实体转DTO
     *
     * @param attach  实体
     * @return DTO
     */
    @Simple
    AttachDTO toDTO(PurchaseAttach attach);

    /**
     * 实体集合转DTO集合
     *
     * @param attaches 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<AttachDTO> toList(List<PurchaseAttach> attaches);


    /**
     * 忽略采购类型附件的id
     *
     * @param entity  采购附件
     * @return 采购附件
     */
    @Simple
    @Mapping(target = "id", ignore = true)
    PurchaseAttach toAttach(PurchaseAttach entity);


    /**
     * 忽略采购类型附件集合的id
     *
     * @param attaches 采购附件集合
     * @return 采购附件集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<PurchaseAttach> toAttaches(List<PurchaseAttach> attaches);
}
