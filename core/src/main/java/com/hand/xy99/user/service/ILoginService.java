package com.hand.xy99.user.service;

import com.hand.xy99.user.dto.User;

/**
 *
 * @author xieshuai
 * @date 2018/4/8
 */
public interface ILoginService {
    public String selectUserById();

    public boolean hasMatchUser(String name, String password);

    public User findUserByUsername(String username);

    public void loginSuccess(User user) ;
}