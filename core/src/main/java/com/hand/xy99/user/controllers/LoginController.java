package com.hand.xy99.user.controllers;

/**
 * Created by xieshuai on 2018/4/7.
 */
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.hand.xy99.user.dto.User;
import com.hand.xy99.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping(value = "/index.html")
    public String loginPage() {
        return "login";
    }
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand){
        boolean isVaildUser = userService.hasMatchUser(loginCommand.getUsername(), loginCommand.getPassword());
        logger.debug( "xieshuai11111111111111111111111111111" );
        if(!isVaildUser){
            return new ModelAndView("login", "error", "用户名或密码错误");
        }else{
            User user = userService.findUserByUsername(loginCommand.getUsername());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user", user);
            logger.debug( "xieshuai11111111111111111111111111111" );
            return new ModelAndView("main");
        }
    }
}
