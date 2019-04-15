package com.hand.xy99.util;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * doc文档转pdf
 *
 * @author shuai.xie
 */
public class DocToPdf {

    private static Logger logger = LoggerFactory.getLogger(DocToPdf.class);

    /**
     * 获取license
     *
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = DocToPdf.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否为空
     *
     * @param obj 字符串对象
     * @return
     */
    protected static boolean notNull(String obj) {
        if (obj != null && !obj.equals("") && !obj.equals("undefined")
                && !obj.trim().equals("") && obj.trim().length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * doc to pdf
     *
     * @param docPath doc源文件
     * @param pdfPath pdf目标文件
     */
    public static void doc2PDF(String docPath, String pdfPath) {
        try {
            // 验证License
            if (!getLicense()) {
                return;
            }

            if (notNull(docPath) && notNull(pdfPath)) {
                File file = new File(pdfPath);
                FileOutputStream os = new FileOutputStream(file);
                Document doc = new Document(docPath);

                doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
                logger.info(pdfPath + "存储成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        DocToPdf word = new DocToPdf();
//     word.toWord("c:\\test1.docx","c:\\signDoc.docx",map);

        word.doc2PDF("D:\\MongoDB入门手册.docx", "D:\\DoctoPdf.pdf");
    }

}