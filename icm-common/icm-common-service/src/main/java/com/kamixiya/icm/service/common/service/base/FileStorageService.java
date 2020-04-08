package com.kamixiya.icm.service.common.service.base;

import java.io.File;
import java.io.IOException;

/**
 * 存储文件的接口, 各种类型的文件存储服务需要实现此接口。
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
public interface FileStorageService {

    /**
     * 保存一个文件。保存时，系统会自动移除时间戳，并重新生成一个内部文件名，也可称为文件的key，取文件时，需要使用此值。
     *
     * @param file 要保存的文件,
     * @return 文件的key, 可以通过该key取回文件
     * @throws IOException 保存失败则抛出异常
     */
    String save(File file) throws IOException;

    /**
     * 通过Key来找回之前保存的文件
     *
     * @param fileKey 保存时返回的key
     * @return 如果文件存在，则返回文件
     * @throws IOException 无法找到文件或失败则抛出异常
     */
    File get(String fileKey) throws IOException;

    /**
     * 删除保存的文件
     *
     * @param fileKey 文件的key
     */
    void delete(String fileKey);

}
