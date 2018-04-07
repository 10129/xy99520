package com.hand.xy99.user.service;

/**
 * Created by xieshuai on 2018/4/7.
 */
import com.hand.xy99.user.dao.LoginLogDao;
import com.hand.xy99.user.dao.UserDao;
import com.hand.xy99.user.dto.LoginLog;
import com.hand.xy99.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userdao;
    @Autowired
    private LoginLogDao loginLogDao;

    public boolean hasMatchUser(String name, String password) {
        int matchCount = userdao.getMatchCount(name, password);
        return matchCount > 0;
    }

    public User findUserByUsername(String username) {
        return userdao.findUserByName(username);
    }

    public void loginSuccess(User user) {
        user.setCredits(user.getCredits() + 5);
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userdao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
    }
}
