package com.hand.xy99.user.service.lmpl;

import com.hand.xy99.user.dto.User;
import com.hand.xy99.user.service.IUserService;

import java.util.List;

/**
 * Created by xieshuai on 2018/1/19.
 */
public class UserServiceImpl extends BaseDAOImpl implements IUserService {

    @Override
    public String selecttest() {
        return "aaa";
    }

    @Override
    public List<User> select(User user) {
        return null;
    }
}
