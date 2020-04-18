package com.kamixiya.icm.service.content.mapper.budget;

import com.kamixiya.icm.model.content.budget.indexLibrary.IndexLibraryDTO;
import com.kamixiya.icm.persistence.content.entity.budget.indexLibrary.IndexLibrary;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * IndexLibraryMapper
 *
 * @author Zhu Jie
 * @date 2020/4/16
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, imports = {Arrays.class})
public interface IndexLibraryMapper {
    /**
     * 实体类转换为DTO类
     *
     * @param entity  实体
     * @return DTO
     */
    @Simple
    IndexLibraryDTO toDTO(IndexLibrary entity);

    /**
     * 实体集合转换为DTO集合
     *
     * @param entity 实体集合
     * @return DTO集合
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<IndexLibraryDTO> toList(Collection<IndexLibrary> entity);

    /**
     * DTO类转换为实体类
     *
     * @param data DTO
     * @return 实体
     */
    IndexLibrary toEntity(IndexLibraryDTO data);
}
