package com.hand.xy99.user.mapper;

import com.hand.xy99.user.dto.LoginLog;
import com.hand.xy99.user.dto.User;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xieshuai on 2018/1/19.
 */
public interface UserMapper {


    public  String selectUserById();

    public void insertLoginLog(LoginLog loginLog);

    public int getMatchCount(@Param("name") String name,@Param("password") String password);

    public User findUserByName(@Param("name") String name);

    public void updateLoginInfo(User user);

}
