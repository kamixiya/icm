package com.kamixiya.icm.service.content.mapper.revenue;

import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningAttachEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.revenue.opening.BankAccountOpeningAttach;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 银行开户申请附件实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankAccountOpeningAttachMapper {
    /**
     * 编辑信息转实体
     *
     * @param createInfoDTO 编辑信息
     * @param context       上下文
     * @return 实体
     */
    @Simple
    @Mapping(target = "bankAccountOpening", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "showOrder", ignore = true)
    BankAccountOpeningAttach toEntity(BankAccountOpeningAttachEditInfoDTO createInfoDTO, @Context CycleAvoidingMappingContext context);

}
