package com.hand.weiapi.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xieshuai on 2018/1/18.
 */
public interface IWeixinService {
    public String processRequest(HttpServletRequest req);
}
