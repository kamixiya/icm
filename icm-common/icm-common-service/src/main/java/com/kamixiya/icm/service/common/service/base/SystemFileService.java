package com.kamixiya.icm.service.common.service.base;

import com.kamixiya.icm.model.base.SystemFileDTO;
import com.kamixiya.icm.model.common.PageDataDTO;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * SystemFileService
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
public interface SystemFileService {
    /**
     * 保存文件
     *
     * @param type  类型，用于标识文件属于某一业务种类，并非文件的格式
     * @param files 要保存的文件
     * @return 已保存的文件信息
     * @throws IOException 找不到ID对应的文件
     */
    SystemFileDTO[] persist(String type, File[] files) throws IOException;

    /**
     * 删除文件
     *
     * @param fileId 文件id
     */
    void deleteFile(String fileId);


    /**
     * 根据文件ID查询文件
     *
     * @param fileId 文件ID
     * @return SystemFileDTO
     */
    SystemFileDTO findById(String fileId);

    /**
     * 复制文件
     *
     * @param type 文件的类型
     * @param id   文件的ID
     * @return 文件对象
     * @throws IOException 文件流异常
     */
    SystemFileDTO copy(String type, String id) throws IOException;

    /**
     * 通过保存的文件名来取文件
     *
     * @param name 文件名或key
     * @return 保存的文件
     * @throws IOException 找不到ID对应的文件
     */
    File getFile(String name) throws IOException;


    /**
     * 识别图片或pdf文件转成txt文件
     *
     * @param resourceFile 图片或pdf文件
     * @return SystemFileDTO
     * @throws IOException        无法找到文件或失败则抛出异常
     * @throws TesseractException 图片识别错误抛出异常
     */
    SystemFileDTO getTxtFile(File resourceFile) throws IOException, TesseractException;

    /**
     * 分页查询文件
     *
     * @param page 页号
     * @param size 每页纪录条数
     * @param sort 排序字段, 例如：字段1,asc,字段2,desc
     * @param type 文件类型
     * @return 全局参数配置dto信息集合
     */
    PageDataDTO<SystemFileDTO> findOnePage(int page, int size, String sort, String type);

    /**
     * 分页查询文件
     *
     * @param page        页号
     * @param size        每页纪录条数
     * @param sort        排序字段, 例如：字段1,asc,字段2,desc
     * @param type        文件类型
     * @param referenceId 引用id
     * @return 全局参数配置dto信息集合
     */
    PageDataDTO<SystemFileDTO> findOnePage(int page, int size, String sort, String type, String referenceId);

    /**
     * 分页查询所有文件
     *
     * @param type        文件类型
     * @param referenceId 引用id
     * @return 全局参数配置dto信息集合
     */
    List<SystemFileDTO> findAll(String type, String referenceId);

}
