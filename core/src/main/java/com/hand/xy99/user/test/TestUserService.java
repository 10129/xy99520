package com.hand.xy99.user.test;

/**
 * Created by xieshuai on 2018/4/7.
 */

import static org.junit.Assert.*;

import java.util.Date;

import com.hand.xy99.user.dto.User;
import com.hand.xy99.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class TestUserService {
    @Autowired
    private UserService userService;

    @Test
    public void hasMatchedUser() {
        boolean b1 = userService.hasMatchUser("admin", "123456");
        boolean b2 = userService.hasMatchUser("admin", "1236");

        assertTrue(b1);
        assertTrue(!b2);
    }

    @Test
    public void findUserByUsername(){
        User user = userService.findUserByUsername("admin");
        assertEquals(user.getUserName(), "admin");
        user.setLastIp("39.108.98.36");
        user.setLastVisit(new Date());
        userService.loginSuccess(user);
    }

}