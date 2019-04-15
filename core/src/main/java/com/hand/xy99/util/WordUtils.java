package com.hand.xy99.util;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * word生成文件
 * ftl模板生成参考 https://www.cnblogs.com/java-class/p/4686325.html
 */
public class WordUtils {
    private static Logger logger = LoggerFactory.getLogger(WordUtils.class);
    /**
     * 导出freemarker渲染后的word （导出到浏览器）
     * @param map 数据
     * @param ftlTemplatePath ftl模板路径
     * @param ftlFile ftl模板名称
     * @param docTemplateName doc模板名称
     * @param newDocName 新生成的doc名称
     * @throws IOException
     */
    public  void exportWord(HttpServletResponse response,Map map, String ftlTemplatePath,String ftlFile,String docTemplateName, String newDocName ) throws IOException {
        //这里注意的是利用WordUtils的类加载器动态获得模板文件的位置
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File(ftlTemplatePath));

        Template freemarkerTemplate = configuration.getTemplate(ftlFile);
        File file = null;
        InputStream fin = null;
        OutputStream out = null;
        try {
            // 调用工具类的createDoc方法生成Word文档
            file = createDoc(map, freemarkerTemplate,docTemplateName);
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件名
            String fileName = newDocName + UUID.randomUUID() + ".doc";
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            out=response.getOutputStream();
            byte[] buffer = new byte[512];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

    /**
     * 导出freemarker渲染后的word
     * @param map 数据
     * @param ftlTemplatePath ftl模板路径
     * @param ftlFile ftl模板名称
     * @param docTemplateName doc模板名称
     * @param newDocPathAndName 新生成的doc文件路径
     * @throws IOException
     */
    public static void exportWordByFreemarkerftl(Map map, String ftlTemplatePath,String ftlFile,String docTemplateName, String newDocPathAndName ) throws IOException {
        //这里注意的是利用WordUtils的类加载器动态获得模板文件的位置
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File(ftlTemplatePath));

        Template freemarkerTemplate = configuration.getTemplate(ftlFile);
        File file = null;
        InputStream fin = null;
        OutputStream out = null;
        try {
            // 调用工具类的createDoc方法生成Word文档
            file = createDoc(map, freemarkerTemplate,docTemplateName);
            fin = new FileInputStream(file);
            out = new FileOutputStream(newDocPathAndName);
            byte[] buffer = new byte[512];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if (fin != null) fin.close();
            if (out != null) out.close();
            if (file != null) file.delete(); // 删除临时文件
        }
    }

    private static File createDoc(Map<?, ?> dataMap, Template template,String templateName) {
        File f = new File(templateName);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();// 取当前日期。
        //获得数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "12324354");
        map.put("sex", "12324354");
        try {
            WordUtils.exportWordByFreemarkerftl(map, "C:/Users/xies/Desktop","sellPlan.ftl", "sellPlan.doc","d:/test1.doc" );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
