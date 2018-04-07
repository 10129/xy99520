package com.hand.xy99.user.dao;

/**
 * Created by xieshuai on 2018/4/7.
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hand.xy99.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;



@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getMatchCount(String name, String password) {
        String sql = "SELECT count(*) FROM t_user WHERE user_name = ? and password = ?";
        return   jdbcTemplate.queryForObject(sql, new Object[] { name, password },Integer.class);
    }

    public User findUserByName(final String name) {
        String sql = "select user_id,user_name,credits from t_user where user_name = ?";
        final User user = new User();
        jdbcTemplate.query(sql, new Object[] { name }, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(name);
                user.setCredits(rs.getInt("credits"));
            }
        });
        return user;
    }

    public void updateLoginInfo(User user) {
        String sql = "update t_user set last_visit = ?,last_ip = ?, credits = ? where user_id = ?";
        jdbcTemplate.update(sql,
                new Object[] { user.getLastVisit(), user.getLastIp(), user.getCredits(), user.getUserId() });
    }
}