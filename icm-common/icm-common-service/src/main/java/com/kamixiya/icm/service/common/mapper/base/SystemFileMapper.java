package com.kamixiya.icm.service.common.mapper.base;

import com.kamixiya.icm.model.base.SystemFileDTO;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.mapper.Simple;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 实体类SystemFile与SystemFileDTO的转换映射
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SystemFileMapper {

    /**
     * 将SystemFile转换为SystemFileDTO
     *
     * @param systemFile 上传的文件实体
     * @return 转换后的DTO
     */
    @Simple
    SystemFileDTO toSystemFileDTO(SystemFile systemFile);

    /**
     * 将SystemFile数组转换为toArraySysFileDTO数组
     *
     * @param systemFiles 上传文件实体
     * @return 转换后的DTO
     */
    @IterableMapping(qualifiedBy = Simple.class)
    SystemFileDTO[] toArraySysFileDTO(SystemFile[] systemFiles);

    /**
     * 将systemFileDTO转换为SystemFile
     *
     * @param systemFileDTO 上传文件DTO
     * @return 转换后的实体
     */
    SystemFile toEntity(SystemFileDTO systemFileDTO);


    /**
     * 将configurations集合转换为ConfigurationDTO集合
     *
     * @param systemFiles 文件集合
     * @return 转换后的 List<SystemFileDTO>
     */
    @IterableMapping(qualifiedBy = Simple.class)
    List<SystemFileDTO> toListSystemFileDTO(List<SystemFile> systemFiles);


}
