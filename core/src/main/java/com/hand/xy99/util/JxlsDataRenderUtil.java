package com.hand.xy99.util;

import com.sun.media.sound.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * jxls excel数据渲染工具类
 */
public class JxlsDataRenderUtil {
    private static Logger logger = LoggerFactory.getLogger(JxlsDataRenderUtil.class);

    /**
     * Excel数据渲染 生成文件响应到前端
     * @param response
     * @param dataMap
     * @param TemplateFileName
     * @param filename
     * @return
     */
    public Boolean  excelDataRender(HttpServletResponse response, Map<String,Object> dataMap, String TemplateFileName, String filename){
        boolean st=true;
        XLSTransformer transformer = new XLSTransformer();
        InputStream in=null;
        OutputStream out=null;
        try {
            // 下面几行是为了解决文件名乱码的问题
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            //得到文件
            //File file = ResourceUtils.getFile("classpath:/conf/testExcel.xlsx");
            String file= this.getClass().getResource("/").getFile();
            in=new BufferedInputStream(new FileInputStream(file+TemplateFileName));
            out = response.getOutputStream();
            Workbook workbook= null;
            workbook = transformer.transformXLS(in, dataMap);
            workbook.write(out);
            out.flush();
            // 把下载地址返回给前端
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            logger.debug("debug: InvalidFormatException"+e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in!=null){try {in.close();} catch (IOException e) {}}
            if (out!=null){try {out.close();} catch (IOException e) {}}
        }
        return  st;
    }
    /**
     * Excel数据渲染
     * @param dataMap
     * @param TemplateFileName
     * @return 返回生成生成文件路径
     */
    public String  excelDataRender( Map<String,Object> dataMap,String TemplateFileName ){
        XLSTransformer transformer = new XLSTransformer();
        InputStream in=null;
        FileOutputStream output=null;
        String filename="";
        try {
            //得到文件
            String file = (String) this.getClass().getResource("/").getFile()
                    .replace("hap", "oos") + "com/excel/";
            in=new BufferedInputStream(new FileInputStream(file+TemplateFileName));

            //输出Excel文件1
            Random random =new Random();
            int i=random.nextInt(10000);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
            String il=df.format(new Date())+i;
            filename=file+"tempEmailExcel_"+il+".xlsx";
            output=new FileOutputStream(filename);
            Workbook workbook= null;
            workbook = transformer.transformXLS(in, dataMap);
            workbook.write(output);
            output.flush();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            logger.debug("debug: InvalidFormatException"+e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in!=null){try {in.close();} catch (IOException e) {}}
            if (output!=null){try {output.close();} catch (IOException e) {}}
        }
        return  filename;
    }

}


