package com.hand.xy99.user.service.lmpl;
import com.hand.xy99.user.controllers.LoginController;
import com.hand.xy99.user.dto.LoginLog;
import com.hand.xy99.user.dto.User;
import com.hand.xy99.user.mapper.UserMapper;
import com.hand.xy99.user.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Created by xieshuai on 2018/4/8.
 */
@Service
public class LoginServicelmpl implements ILoginService {

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


    @Override
    public boolean hasMatchUser(String name, String password) {
        int matchCount = userMapper.getMatchCount(name, password);
        return matchCount > 0;
    }
    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByName(username);
    }
    @Override
    public void loginSuccess(User user) {
        user.setCredits(user.getCredits() + 5);
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userMapper.updateLoginInfo(user);
        userMapper.insertLoginLog(loginLog);
    }
}
