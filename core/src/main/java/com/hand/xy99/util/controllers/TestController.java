package com.hand.xy99.util.controllers;

import com.hand.xy99.user.dto.User;
import com.hand.xy99.util.WordUtils;
import com.hand.xy99.util.annotation.MyAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response){
        //获得数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "12324354");
        map.put("sex", "1232431");
        try {
            new WordUtils().exportWord(response,map, "C:/Users/xies/Desktop","sellPlan.ftl", "sellPlan.doc","test1" );
        } catch (IOException e) {
            e.printStackTrace();
        }
     return "test !";
    }

    @MyAnnotation()
    @RequestMapping(value = "/testFreemarker")
    @ResponseBody
    public String testFreemarker(User User) throws Exception {

        return "12345";
    }

}
