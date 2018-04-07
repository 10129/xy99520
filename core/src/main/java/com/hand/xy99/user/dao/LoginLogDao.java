package com.hand.xy99.user.dao;

/**
 * Created by xieshuai on 2018/4/7.
 */
import com.hand.xy99.user.dto.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertLoginLog(LoginLog loginLog) {
        String sql = "insert into t_login_log(user_id, ip,login_datatime) values(?,?,?)";
        Object[] pra = { loginLog.getUserId(), loginLog.getIp(), loginLog.getLoginDate() };
        jdbcTemplate.update(sql, pra);
    }
}
