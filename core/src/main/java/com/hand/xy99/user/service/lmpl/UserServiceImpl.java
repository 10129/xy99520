package com.hand.xy99.user.service.lmpl;

import com.hand.xy99.user.controllers.LoginController;
import com.hand.xy99.user.mapper.UserMapper;
import com.hand.xy99.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by xieshuai on 2018/4/8.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired(required=false)
    private UserMapper userMapper;
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Override
    public String selectUserById(){
        String aa ="qw";
        try {
             aa =userMapper.selectUserById();
        }catch (Exception e){
            e.printStackTrace();
            logger.debug( "错误："+e);
        }
        return aa;

    }
}
