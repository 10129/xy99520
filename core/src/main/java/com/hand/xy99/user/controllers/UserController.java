package com.hand.xy99.user.controllers;

import com.hand.xy99.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by xieshuai on 2018/1/19.
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

   
}