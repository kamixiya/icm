package com.kamixiya.icm.core.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.util.ResourceUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 使用Tesseract识别图片文件，生成txt文件
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
class OcrToTxtUtil {
    private OcrToTxtUtil() {
    }

    static String ocrService(File imageFile, String language) throws IOException, TesseractException {
        String message;
        // 优化图片文件
        BufferedImage textImage = ClearImage.cleanImage(imageFile, imageFile.getParent());
        Tesseract instance = new Tesseract();
        // 获取resource中的文字库
        File tessFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "tessdata");
        //设置训练库
        instance.setDatapath(tessFile.getAbsolutePath());
        //中文识别
        if (language != null) {
            instance.setLanguage(language);
        }
        message = instance.doOCR(textImage);
        return message;
    }


}

