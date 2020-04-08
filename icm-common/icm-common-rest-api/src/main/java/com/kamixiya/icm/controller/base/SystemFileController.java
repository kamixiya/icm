package com.kamixiya.icm.controller.base;

import com.kamixiya.icm.core.preview.OfficeConverter;
import com.kamixiya.icm.model.base.SystemFileDTO;
import com.kamixiya.icm.model.common.PageDataDTO;
import com.kamixiya.icm.service.common.exception.FileFormatErrorException;
import com.kamixiya.icm.service.common.service.base.SystemFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SystemFileController
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Api(tags = {"文件管理"})
@RestController
@Slf4j
@RequestMapping("/api/base/files")
@Validated
public class SystemFileController {
    private static final int BUFFER_SIZE = 8192;
    private final SystemFileService systemFileService;
    /**
     * 上传时需要拦截的文件后缀，eg:.jsp.asp.exe
     * 发现文件后缀相同则抛异常
     */
    private String fileFilterType;
    /**
     * 预览转换后的文件类型
     */
    private String fileType;
    private String tempPath;
    private String tempDirFiles;

    @Autowired
    public SystemFileController(SystemFileService systemFileService,
                                @Value("${zj.kamixiya.upload.temp-dir-files}") String tempDirFiles,
                                @Value("${zj.kamixiya.upload.temp-path}") String tempPath,
                                @Value("${zj.kamixiya.preview.file-type}") String fileType,
                                @Value("${zj.kamixiya.upload.file-filter-type}") String fileFilterType) {
        this.systemFileService = systemFileService;
        this.tempDirFiles = tempDirFiles;
        this.tempPath = tempPath;
        this.fileType = fileType;
        this.fileFilterType = fileFilterType;
        try {
            FileUtils.forceMkdir(new File(tempPath));
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * MultipartFile转成File
     *
     * @param file MultipartFile
     * @return File
     * @throws IOException IO异常
     */
    private File saveFile(MultipartFile file) throws IOException {
        String fileName = FilenameUtils.getName(file.getOriginalFilename());
        String path = tempPath + getCurrentTimestamp() + File.separator;
        FileUtils.forceMkdir(new File(path));
        File newFile = new File(path + fileName);
        file.transferTo(newFile);
        return newFile;
    }

    /**
     * 格式化时间
     *
     * @return 返回格式化后的时间
     */
    private String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }


    @ApiOperation(value = "上传单个文件")
    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SystemFileDTO uploadSingleFile(
            @ApiParam(value = "文件分类", required = true) @RequestParam("type") String type,
            @ApiParam(value = "文件", required = true) @RequestPart("file") MultipartFile file) throws IOException, FileFormatErrorException {
        if (fileFilterType != null) {
            String fileName = file.getOriginalFilename();
            String suffix = FilenameUtils.getExtension(fileName);
            if ((suffix == null) || fileFilterType.contains(suffix)) {
                throw new FileFormatErrorException();
            }
        }
        File savedFile = saveFile(file);
        try {
            return systemFileService.persist(type, new File[]{savedFile})[0];
        } finally {
            FileUtils.deleteQuietly(
                    new File(FilenameUtils.getPathNoEndSeparator(savedFile.getCanonicalPath()))
            );
        }
    }

    @ApiOperation(value = "上传多个文件")
    @PostMapping(value = "/multiple", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SystemFileDTO[] uploadMultiFiles(
            @ApiParam(value = "文件分类", required = true) @RequestParam("type") String type,
            @ApiParam(value = "文件", required = true, allowMultiple = true) @RequestPart("files") MultipartFile[] files) throws IOException, FileFormatErrorException {
        if (fileFilterType != null) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String suffix = FilenameUtils.getExtension(fileName);
                if ((suffix == null) || fileFilterType.contains(suffix)) {
                    throw new FileFormatErrorException();
                }
            }
        }
        File[] savedFiles = new File[files.length];
        int i = 0;
        for (MultipartFile f : files) {
            savedFiles[i++] = saveFile(f);
        }
        try {
            return systemFileService.persist(type, savedFiles);
        } finally {
            for (File f : savedFiles) {
                FileUtils.deleteQuietly(
                        new File(FilenameUtils.getPathNoEndSeparator(f.getCanonicalPath()))
                );
            }
        }
    }

    @ApiOperation(value = "删除指定文件")
    @DeleteMapping(value = "/{fileId}")
    public void deleteFile(@ApiParam(value = "要删除的文件的ID", required = true) @PathVariable String fileId) {
        systemFileService.deleteFile(fileId);
    }

    @ApiOperation(value = "取指定文件")
    @GetMapping(value = "/{fileId}")
    public SystemFileDTO getFile(
            @ApiParam(value = "文件ID", required = true) @PathVariable String fileId) {
        return systemFileService.findById(fileId);
    }

    @ApiOperation(value = "预览指定文件(转成pdf后预览)")
    @GetMapping(value = "/{id}/preview")
    public void previewFile(
            @ApiParam(value = "文件ID", required = true) @PathVariable String id,
            HttpServletResponse response) throws Exception {
        SystemFileDTO systemFileDTO = systemFileService.findById(id);
        String fileName = systemFileDTO.getName();
        File file = systemFileService.getFile(fileName);
        String type = FilenameUtils.getExtension(systemFileDTO.getOriginalName());
        String pdfPath = tempDirFiles + fileName + fileType;
        File outFile = new File(pdfPath);
        String filterType = fileType.substring(1);
        String fileNameSuffix = null;
        // 已经是pdf文件，则不再转换
        if (filterType.equals(type)) {
            outFile = file;
        }

        // 将上次转换失败的文件删除
        if (outFile.exists() && outFile.length() == 0) {
            Files.delete(outFile.toPath());
        }

        if (!filterType.equals(type) && !FileUtils.directoryContains(new File(tempDirFiles), outFile)) {
            if (outFile.createNewFile()) {
                OfficeConverter.toPdf(file.getCanonicalPath(), outFile.getCanonicalPath());
            } else {
                FileUtils.forceDelete(file);
                throw new IOException();
            }
        }

        try {
            fileNameSuffix = FilenameUtils.getExtension(outFile.getName());
            fileName = URLEncoder.encode(FilenameUtils.getBaseName(outFile.getName()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn(e.getMessage());
        }
        try (FileInputStream fis = new FileInputStream(outFile);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()
        ) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName*=UTF-8''" + fileName + "." + fileNameSuffix);
            byte[] buffer = new byte[BUFFER_SIZE];
            int i;
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } finally {
            FileUtils.forceDelete(file);
        }

    }

    @ApiOperation(value = "取指定文件的内容, 通常用于页面直接显示图片等文件的内容")
    @GetMapping(value = "/{fileId}/content")
    public void getFileContent(
            @ApiParam(value = "文件ID", required = true) @PathVariable String fileId,
            HttpServletResponse response) throws IOException {
        download(fileId, response, true);
    }

    @ApiOperation(value = "下载指定文件, 触发browser显示保存对话框")
    @GetMapping(value = "/{fileId}/download")
    public void downloadFile(
            @ApiParam(value = "文件ID", required = true) @PathVariable String fileId,
            HttpServletResponse response) throws IOException {
        download(fileId, response, false);
    }


    private void download(String fileId, HttpServletResponse response, boolean isGetFileContent) throws IOException {
        SystemFileDTO systemFileDTO = systemFileService.findById(fileId);
        String fileName = systemFileDTO.getName();
        File file = systemFileService.getFile(fileName);
        String fileNameSuffix = null;
        try {
            fileNameSuffix = FilenameUtils.getExtension(systemFileDTO.getOriginalName());
            fileName = URLEncoder.encode(FilenameUtils.getBaseName(systemFileDTO.getOriginalName()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn(e.getMessage());
        }

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()
        ) {
            if (isGetFileContent) {
                // 获取文件后缀
                String suffix = FilenameUtils.getExtension(systemFileDTO.getOriginalName());
                // 获取文件流中ContentTypeMap表
                Map<String, String> map = streamType();
                if (suffix != null && map.get(suffix) != null) {
                    response.setContentType(map.get(suffix));
                }
            } else {
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName*=UTF-8''" + fileName + "." + fileNameSuffix);
            }
            byte[] buffer = new byte[BUFFER_SIZE];
            int i;
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } finally {
            FileUtils.forceDelete(file);
        }
    }

    @ApiOperation(value = "分页查询文件")
    @GetMapping(value = "", params = {"page", "size", "sort"})
    public PageDataDTO<SystemFileDTO> findOnePage(
            @ApiParam(value = "页号，从0开始", required = true, defaultValue = "0") @RequestParam("page") @Min(0) int page,
            @ApiParam(value = "每页纪录条数", required = true, defaultValue = "20") @RequestParam("size") @Min(1) @Max(100) int size,
            @ApiParam(value = "排序字段, 例如：字段1,asc,字段2,desc") @RequestParam(value = "sort", required = false, defaultValue = "createdTime,desc") String sort,
            @ApiParam(value = "文件类型") @RequestParam(value = "type", required = false) String type,
            @ApiParam(value = "引用id") @RequestParam(value = "referenceId", required = false) String referenceId) {
        return systemFileService.findOnePage(page, size, sort, type, referenceId);
    }

    @ApiOperation(value = "查询全部文件")
    @GetMapping(value = "/all")
    public List<SystemFileDTO> findAll(
            @ApiParam(value = "文件类型") @RequestParam(value = "type", required = false) String type,
            @ApiParam(value = "引用ID") @RequestParam(value = "referenceId", required = false) String referenceId
    ) {
        return systemFileService.findAll(type, referenceId);
    }

    /**
     * 文件流中ContentType的Map表
     *
     * @return Map
     */
    private Map<String, String> streamType() {
        Map<String, String> streamType = new HashMap<>(16);
        final String excel = "application/vnd.ms-excel";
        streamType.put("pdf", "application/pdf");
        streamType.put("doc", "application/msword");
        streamType.put("dot", "application/msword");
        streamType.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        streamType.put("xla", excel);
        streamType.put("xlt", excel);
        streamType.put("xls", excel);
        streamType.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        streamType.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        streamType.put("ppt", "application/ms-powerpoint");
        streamType.put("zip", "application/zip");
        streamType.put("htm", "text/html");
        streamType.put("html", "text/html");
        streamType.put("jpg", "image/jpeg");
        streamType.put("jpeg", "image/jpeg");
        streamType.put("png", "image/png");
        streamType.put("gif", "image/gif");
        streamType.put("txt", "text/plain");

        return streamType;
    }

}
