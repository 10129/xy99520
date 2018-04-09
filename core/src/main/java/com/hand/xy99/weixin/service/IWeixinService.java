package com.hand.xy99.weixin.service;

import com.hand.xy99.weixin.pojo.message.menu.Menu;
import com.hand.xy99.weixin.pojo.Token;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xieshuai on 2018/1/18.
 */
public interface IWeixinService {
    /**
     * 消息处理
     * @param req
     * @return
     */
    public String processRequest(HttpServletRequest req);
    /**
     * 得到菜单
     * @param accessToken
     * @return
     */
    public Menu getMenu(Token accessToken);
}
