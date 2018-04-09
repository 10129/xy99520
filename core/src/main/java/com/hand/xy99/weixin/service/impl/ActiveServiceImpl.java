package com.hand.xy99.weixin.service.impl;

import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weixin.pojo.WeixinUserInfo;
import com.hand.xy99.weixin.service.IActiveService;

import static com.hand.xy99.weixin.weChatServlet.AccessTokenServlet.getUserInfo;

/**@desc  : 发送消息
 * @author: shuai.xie
 * @date  : 2017-8-18 上午10:06:23
 */
public class ActiveServiceImpl implements IActiveService {
    @Override
    public void getUser(){
        // 获取接口访问凭证
        String accessToken = AccessTokenInfo.accessToken.getAccessToken();
        /**
         * 获取用户信息
         */
        WeixinUserInfo user = getUserInfo(accessToken, "ooK-yuJvd9gEegH6nRIen-gnLrVw");
        System.out.println("OpenID：" + user.getOpenId());
        System.out.println("关注状态：" + user.getSubscribe());
        System.out.println("关注时间：" + user.getSubscribeTime());
        System.out.println("昵称：" + user.getNickname());
        System.out.println("性别：" + user.getSex());
        System.out.println("国家：" + user.getCountry());
        System.out.println("省份：" + user.getProvince());
        System.out.println("城市：" + user.getCity());
        System.out.println("语言：" + user.getLanguage());
        System.out.println("头像：" + user.getHeadImgUrl());
    }

}
