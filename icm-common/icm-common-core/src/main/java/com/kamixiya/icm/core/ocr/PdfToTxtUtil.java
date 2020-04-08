package com.kamixiya.icm.core.ocr;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * PdfToTxtUtil
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
public class PdfToTxtUtil {

    private PdfToTxtUtil() {
    }

    static void getTextFromPDF(File pdfFile, File txtFile) throws IOException {
        try (FileWriter writer = new FileWriter(txtFile);
             FileInputStream is = new FileInputStream(pdfFile);
             PDDocument document = PDDocument.load(is)
        ) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(false);
            stripper.writeText(document, writer);
        }
    }
}
