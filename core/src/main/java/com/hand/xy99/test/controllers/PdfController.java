package com.hand.xy99.test.controllers;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hand.xy99.test.dto.ResponseData;
import com.hand.xy99.util.PdfUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/api"})
public class PdfController  {


    @RequestMapping(value = "/output/{fileName}.pdf",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData outputPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) {
        try {
            //输出PDF
            OutputStream os= response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "application/pdf;charset=utf-8");
            //response.addHeader("Content-Disposition","attachment;filename=" + new String("预算汇总.pdf".getBytes(),"utf-8"));
            new PdfUtil().outputPdfFile(os,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}