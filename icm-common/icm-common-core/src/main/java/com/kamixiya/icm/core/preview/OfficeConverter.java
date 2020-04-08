package com.kamixiya.icm.core.preview;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Office文件转换为pdf工具
 *
 * @author Zhu Jie
 * @date 2020/4/8
 */
public class OfficeConverter {

    private static final String DOC = "doc";
    private static final String DOCX = "docx";
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static final String PPT = "ppt";
    private static final String PPTX = "pptx";
    private static final String PDF = "pdf";
    private static final String TXT = "txt";
    private static final String WPS = "wps";
    private static final String EXTENSION_SEPARATOR = ".";

    private static final String LICENSE_LOCATION = "asponse/license.xml";

    /**
     * hide constructor because this class cannot be instanced
     */
    private OfficeConverter() {
    }

    private static void loadLicense(LicenseType type) throws Exception {
        InputStream is = OfficeConverter.class.getClassLoader().getResourceAsStream(LICENSE_LOCATION);
        switch (type) {
            case WORD:
                com.aspose.words.License license1 = new com.aspose.words.License();
                license1.setLicense(is);
                break;
            case EXCEL:
                com.aspose.cells.License license2 = new com.aspose.cells.License();
                license2.setLicense(is);
                break;
            case PPT:
                com.aspose.slides.License license3 = new com.aspose.slides.License();
                license3.setLicense(is);
                break;
            case TXT:
                com.aspose.slides.License license4 = new com.aspose.slides.License();
                license4.setLicense(is);
                break;
            case WPS:
                com.aspose.slides.License license5 = new com.aspose.slides.License();
                license5.setLicense(is);
                break;
            default:
                is.close();
                throw new IllegalArgumentException("不支持的License类型");
        }

        is.close();
    }

    private static void word2Pdf(String srcFilename, String destFilename) throws Exception {
        loadLicense(LicenseType.WORD);
        com.aspose.words.Document document = new com.aspose.words.Document(srcFilename);
        document.save(new FileOutputStream(destFilename), com.aspose.words.SaveFormat.PDF);
    }

    public static void toPdf(String srcFilename, String destFilename) throws Exception {

        File file = new File(srcFilename);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("要转换的源文件不存在: " + srcFilename);
        }

        String extension = destFilename.substring(destFilename.lastIndexOf(EXTENSION_SEPARATOR) + 1);
        if (!PDF.equalsIgnoreCase(extension)) {
            throw new IllegalArgumentException("只支持转换为pdf文件，不支持: " + extension);
        }

        extension = srcFilename.substring(srcFilename.lastIndexOf(EXTENSION_SEPARATOR) + 1).toLowerCase();
        if (DOC.equals(extension) || DOCX.equals(extension)) {
            word2Pdf(srcFilename, destFilename);
            return;
        }

        if (XLS.equals(extension) || XLSX.equals(extension)) {
            excel2Pdf(srcFilename, destFilename);
            return;
        }

        if (PPT.equals(extension) || PPTX.equals(extension)) {
            ppt2Pdf(srcFilename, destFilename);
            return;
        }

        if (TXT.equals(extension)) {
            word2Pdf(srcFilename, destFilename);
            return;
        }

        if (WPS.equals(extension)) {
            word2Pdf(srcFilename, destFilename);
            return;
        }

        throw new IllegalArgumentException("不支持的文件转换类型: " + extension);

    }

    private static void excel2Pdf(String srcFilename, String destFilename) throws Exception {
        loadLicense(LicenseType.EXCEL);
        Workbook workbook = new Workbook(srcFilename);
        com.aspose.cells.PdfSaveOptions pdfSaveOptions = new com.aspose.cells.PdfSaveOptions();
        pdfSaveOptions.setOnePagePerSheet(true);
        workbook.save(new FileOutputStream(destFilename), pdfSaveOptions);
    }

    private static void ppt2Pdf(String srcFilename, String destFilename) throws Exception {
        loadLicense(LicenseType.PPT);
        Presentation ppt = new Presentation(srcFilename);
        ppt.save(new FileOutputStream(destFilename), com.aspose.slides.SaveFormat.Pdf);
    }

    private enum LicenseType {
        WORD,
        EXCEL,
        PPT,
        TXT,
        WPS
    }


}
