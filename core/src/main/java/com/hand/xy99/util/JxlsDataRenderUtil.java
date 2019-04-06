package com.hand.xy99.util;

import com.sun.media.sound.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;

public class JxlsDataRenderUtil {
    private static Logger logger = LoggerFactory.getLogger(JxlsDataRenderUtil.class);

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

}


