package com.kamixiya.icm.service.common.service.base;

import com.github.wenhao.jpa.Specifications;
import com.kamixiya.icm.core.jpa.PageHelper;
import com.kamixiya.icm.core.ocr.OcrAndPdfUtil;
import com.kamixiya.icm.model.base.SystemFileDTO;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.service.common.entity.base.SystemFile;
import com.kamixiya.icm.service.common.exception.NoSuchDataException;
import com.kamixiya.icm.service.common.mapper.base.SystemFileMapper;
import com.kamixiya.icm.service.common.repository.base.SystemFileRepository;
import com.kamixiya.icm.service.common.utils.PageDataUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * SystemFileServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Slf4j
@Service("systemFileService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SystemFileServiceImpl implements SystemFileService {


    private static final String FILE_TYPE = ".txt";
    private static final String TESS_DATA = "chi_sim";
    private final SystemFileRepository systemFileRepository;
    private final FileStorageService fileStorageService;
    private final SystemFileMapper systemFileMapper;
    private final String tempDir;

    @Autowired
    public SystemFileServiceImpl(SystemFileRepository systemFileRepository,
                                 FileStorageService fileStorageService,
                                 SystemFileMapper systemFileMapper,
                                 @Value("${zj.kamixiya.upload.temp-path}") String tempPath) {
        this.fileStorageService = fileStorageService;
        this.systemFileMapper = systemFileMapper;
        this.systemFileRepository = systemFileRepository;
        this.tempDir = tempPath;
    }

    /**
     * 新建SystemFile对象，并将File信息传入新建SystemFile对象对象
     *
     * @param type 文件类型
     * @param file 文件对象
     * @return SystemFile
     */
    private SystemFile createSystemFile(String type, File file) {
        SystemFile systemFile = new SystemFile();

        // 去除加上的时间戳，取得原文件名
        systemFile.setOriginalName(FilenameUtils.getName(file.getName()));
        systemFile.setSize(file.length());
        systemFile.setType(type);
        return systemFile;
    }


    @Override
    public SystemFileDTO findById(String fileId) {
        Optional<SystemFile> systemFileOptional = systemFileRepository.findById(Long.valueOf(fileId));
        if (!systemFileOptional.isPresent()) {
            throw new NoSuchDataException(fileId);
        }
        return systemFileMapper.toSystemFileDTO(systemFileOptional.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemFileDTO[] persist(String type, File[] files) throws IOException {
        SystemFile systemFile;
        SystemFile[] systemFiles = new SystemFile[files.length];
        int i = 0;
        for (File f : files) {
            systemFile = createSystemFile(type, f);
            systemFile.setName(fileStorageService.save(f));
            systemFileRepository.saveAndFlush(systemFile);
            systemFiles[i++] = systemFile;
        }
        return systemFileMapper.toArraySysFileDTO(systemFiles);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(String fileId) {
        Optional<SystemFile> systemFileOptional = systemFileRepository.findById(Long.valueOf(fileId));
        if (!systemFileOptional.isPresent()) {
            throw new NoSuchDataException(fileId);
        }
        SystemFile systemFile = systemFileOptional.get();
        systemFileRepository.delete(systemFile);
        fileStorageService.delete(systemFile.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemFileDTO copy(String type, String id) throws IOException {
        Optional<SystemFile> systemFileOptional = systemFileRepository.findById(Long.valueOf(id));
        if (!systemFileOptional.isPresent()) {
            throw new NoSuchDataException(id);
        }

        SystemFile systemFile = systemFileOptional.get();
        File file = getFile(systemFile.getName());

        SystemFile systemFileCopy = new SystemFile();
        systemFileCopy.setOriginalName(systemFile.getOriginalName());
        systemFileCopy.setSize(file.length());
        systemFileCopy.setType(type);

        systemFileCopy.setName(fileStorageService.save(file));
        systemFileCopy = systemFileRepository.saveAndFlush(systemFileCopy);
        FileUtils.forceDelete(file);
        return systemFileMapper.toSystemFileDTO(systemFileCopy);
    }


    @Override
    public File getFile(String name) throws IOException {
        return fileStorageService.get(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SystemFileDTO getTxtFile(File resourceFile) throws IOException, TesseractException {
        String fileName = FilenameUtils.getBaseName(resourceFile.getName());
        String fullPath = tempDir + fileName + FILE_TYPE;
        File txtFile = new File(fullPath);
        File txt = OcrAndPdfUtil.ocrOrPdfService(resourceFile, txtFile, TESS_DATA);
        File[] files = new File[]{txt};
        SystemFileDTO[] systemFileDTOS = this.persist("全文检索引用的txt文件", files);
        return systemFileDTOS[0];
    }

    @Override
    public PageDataDTO<SystemFileDTO> findOnePage(int page, int size, String sort, String type) {
        Page<SystemFile> systemFilePage = systemFileRepository.findAll(
                Specifications.<SystemFile>and()
                        .eq(type != null && !"".equals(type) && !"null".equals(type), "type", type)
                        .build(), PageHelper.generatePageRequest(page, size, sort));
        List<SystemFileDTO> list = systemFileMapper.toListSystemFileDTO(systemFilePage.getContent());
        return PageDataUtil.toPageData(systemFilePage, list);
    }

    @Override
    public PageDataDTO<SystemFileDTO> findOnePage(int page, int size, String sort, String type, String referenceId) {
        Page<SystemFile> systemFilePage = systemFileRepository.findAll(
                Specifications.<SystemFile>and()
                        .eq(type != null && !"".equals(type) && !"null".equals(type), "type", type)
                        .eq(referenceId != null && !"".equals(referenceId) && !"null".equals(referenceId), "referenceId", referenceId)
                        .build(), PageHelper.generatePageRequest(page, size, sort));
        List<SystemFileDTO> list = systemFileMapper.toListSystemFileDTO(systemFilePage.getContent());
        return PageDataUtil.toPageData(systemFilePage, list);
    }

    @Override
    public List<SystemFileDTO> findAll(String type, String referenceId) {
        List<SystemFile> systemFiles = systemFileRepository.findAll(
                Specifications.<SystemFile>and()
                        .eq(type != null && !"".equals(type) && !"null".equals(type), "type", type)
                        .eq(referenceId != null && !"".equals(referenceId) && !"null".equals(referenceId), "referenceId", referenceId)
                        .build());
        return systemFileMapper.toListSystemFileDTO(systemFiles);
    }
}

