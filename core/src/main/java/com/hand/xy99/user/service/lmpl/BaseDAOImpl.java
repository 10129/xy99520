package com.hand.xy99.user.service.lmpl;

import com.hand.xy99.user.service.IBaseDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by xieshuai on 2018/1/19.
 */
public class BaseDAOImpl implements IBaseDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void insert(Object obj) {

    }
    @Override
    public void insert(String sql) {
        jdbcTemplate.execute(sql);
    }
    @Override
    public void insertObjects(String[] sqls) {
        jdbcTemplate.batchUpdate(sqls);
    }
    @Override
    public List<Map<String, Object>> select(String sql) {
        return jdbcTemplate.queryForList(sql);
    }
    @Override
    public void update(String how) {
        jdbcTemplate.update(how);

    }
    @Override
    public void delete(String sql) {
        if (sql == null) {
            return;
        }
        jdbcTemplate.execute(sql);
    }
    @Override
    public void edit(String sql) {
        if (sql == null) {
            return;
        }
        jdbcTemplate.execute(sql);
    }
    @Override
    public void execute(String sql, PreparedStatementCallback callback) {
        jdbcTemplate.execute(sql, callback);
    }
    @Override
    public void save(String sql) {
        if (sql == null) {
            return;
        }
        jdbcTemplate.execute(sql);
    }
@Override
    public Connection getConnection() throws Exception {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        return conn;
    }

}
