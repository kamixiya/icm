package com.kamixiya.icm.service.common.service.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 本地文件存储服务，文件存储时会被压缩，当取文件时，文件会被解压。
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Service("fileStorageService")
@Slf4j
public class LocalFileStorageServiceImpl implements FileStorageService {

    private static final int BUFFER_SIZE = 8192;
    private final String fileDir;
    private final String tempDir;


    @Autowired
    public LocalFileStorageServiceImpl(@Value("${zj.kamixiya.upload.temp-path}") String tempPath,
                                       @Value("${zj.kamixiya.upload.file-path}") String filePath) {
        fileDir = filePath;
        tempDir = tempPath;
        try {
            FileUtils.forceMkdir(new File(fileDir));
            FileUtils.forceMkdir(new File(tempDir));
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }


    @Override
    public String save(File file) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String fullPath = fileDir + fileName;

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream source = new BufferedInputStream(fis);
             FileOutputStream fos = new FileOutputStream(fullPath);
             ZipOutputStream dest = new ZipOutputStream(fos)
        ) {

            byte[] buffer = new byte[BUFFER_SIZE];
            ZipEntry zipEntry = new ZipEntry(file.getName());
            dest.putNextEntry(zipEntry);
            int count;
            while ((count = source.read(buffer, 0, BUFFER_SIZE)) != -1) {
                dest.write(buffer, 0, count);
            }
            dest.flush();
            dest.finish();
            return fileName;
        }
    }

    @Override
    public File get(String fileKey) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String fileName = fileDir + fileKey;
        String destFile = tempDir + fileKey;
        try (FileInputStream fis = new FileInputStream(fileName);
             ZipInputStream zin = new ZipInputStream(new BufferedInputStream(fis))
        ) {
            ZipEntry entry;

            byte[] buffer = new byte[BUFFER_SIZE];
            while ((entry = zin.getNextEntry()) != null) {
                destFile = tempDir + uuid + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(destFile);
                     BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER_SIZE)
                ) {
                    int count;
                    while ((count = zin.read(buffer, 0, BUFFER_SIZE)) != -1) {
                        dest.write(buffer, 0, count);
                    }
                    dest.flush();
                }
            }

            return new File(destFile);
        }
    }

    @Override
    public void delete(String fileKey) {
        FileUtils.deleteQuietly(new File(fileDir + fileKey));
        FileUtils.deleteQuietly(new File(tempDir + fileKey));
    }


}

