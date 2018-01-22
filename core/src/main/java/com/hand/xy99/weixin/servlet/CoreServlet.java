package com.hand.xy99.weixin.servlet;

/**
 * Created by hand on 2018/1/21.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hand.xy99.weixin.service.CoreService;
//import com.hand.xy99.weixin.util.SignUtil;

/**
 * 类名: CoreServlet </br>
 * 描述: 来接收微信服务器传来信息 </br>
 * 开发人员： hand.xy99</br>
 * 创建时间：2015-9-29 </br>
 * 发布版本：V1.0 </br>
 */
public class CoreServlet extends HttpServlet {

    private static final long serialVersionUID = 4323197796926899691L;

    /**
     * 处理微信服务器发来的消息
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 调用核心业务类接收消息、处理消息
        String respXml = CoreService.processRequest(request);

        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

}
