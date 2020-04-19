package com.kamixiya.icm.service.content.mapper.revenue;

import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningCreateInfoDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningDTO;
import com.kamixiya.icm.model.content.revenue.opening.BankAccountOpeningEditInfoDTO;
import com.kamixiya.icm.persistence.content.entity.revenue.opening.BankAccountOpening;
import com.kamixiya.icm.service.common.mapper.Simple;
import com.kamixiya.icm.service.common.utils.CycleAvoidingMappingContext;
import org.mapstruct.*;

import java.util.List;

/**
 * 银行开户申请实体与DTO的转换工具
 *
 * @author Zhu Jie
 * @date 2020/4/19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankAccountOpeningMapper {

    /**
     * 实体转DTO
     *
     * @param entity  实体信息
     * @param context 上下文
     * @return DTO
     */
    @Simple
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "lastModifyUser", ignore = true)
    BankAccountOpeningDTO toDTO(BankAccountOpening entity, @Context CycleAvoidingMappingContext context);

    /**
     * 实体集合转DTO集合
     *
     * @param list    实体集合
     * @param context 上下文
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<BankAccountOpeningDTO> toList(List<BankAccountOpening> list, @Context CycleAvoidingMappingContext context);

    /**
     * 编辑信息转实体
     *
     * @param createInfoDTO 编辑信息
     * @param context       上下文
     * @return 实体
     */
    @Simple
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "declarer", ignore = true)
    BankAccountOpening toEntity(BankAccountOpeningCreateInfoDTO createInfoDTO, @Context CycleAvoidingMappingContext context);

    /**
     * 通过编辑信息更新实体
     *
     * @param editInfoDTO 编辑信息
     * @param entity      实体数据
     * @param context     上下文
     */
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "filterPath", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedTime", ignore = true)
    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "declarer", ignore = true)
    @Mapping(target = "bankAccountOpeningAttaches", ignore = true)
    void updateEntity(BankAccountOpeningEditInfoDTO editInfoDTO, @MappingTarget BankAccountOpening entity, @Context CycleAvoidingMappingContext context);

}
