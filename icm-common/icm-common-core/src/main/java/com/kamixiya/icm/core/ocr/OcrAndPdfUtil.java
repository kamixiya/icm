package com.kamixiya.icm.core.ocr;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.Utils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * OcrAndPdfUtil
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
@Slf4j
public class OcrAndPdfUtil {

    private static final String PDF_FILE = "pdf";
    private static final String TXT_FILE = "txt";

    private OcrAndPdfUtil() {
    }

    /**
     * 识别图片或pdf文件生成txt文件
     *
     * @param resourceFile 要识别的文件
     * @param txtFile      识别后写入的txt文件
     * @param language     OCR语言库(例如chi_sim.traineddata,传chi_sim即可)
     * @return txt文件
     */

    public static File ocrOrPdfService(File resourceFile, File txtFile, String language) throws IOException, TesseractException {
        if (!TXT_FILE.equals(FilenameUtils.getExtension(txtFile.getName()))) {
            throw new UnsupportedOperationException("该文件类型不是txt");
        }
        Map<String, String> map = checkFile();
        if (map.get(FilenameUtils.getExtension(resourceFile.getName())) != null) {
            // 图片类型文件调用OCR服务
            if (!PDF_FILE.equals(FilenameUtils.getExtension(resourceFile.getName()))) {
                // 设置图片，与识别语言(chi_sim为中文，为null默认英文)
                String message = OcrToTxtUtil.ocrService(resourceFile, language);
                Utils.writeFile(message.getBytes(), txtFile);
            } else {
                // pdf类型文件调用pdf服务
                PdfToTxtUtil.getTextFromPDF(resourceFile, txtFile);
            }
        } else {
            throw new UnsupportedOperationException("该文件类型不支持");
        }
        return txtFile;
    }

    /**
     * 构成支持的文件后缀集
     */

    private static Map<String, String> checkFile() {
        Map<String, String> map = new HashMap<>(5);
        map.put("png", "png");
        map.put("jpg", "jpg");
        map.put("jpeg", "jpeg");
        map.put("bmp", "bmp");
        map.put("tif", "tif");
        map.put("pdf", "pdf");
        return map;
    }

}

