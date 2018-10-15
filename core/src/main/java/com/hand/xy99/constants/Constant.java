package com.hand.xy99.constants;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    /**
     * 微信APPID、APP_SECRET
     */
    public final static String APPID = "wxd135f4cb0d7346bf";
    public final static String APP_SECRET = "7e55c0ed3b6e16d4ea36c7365f410d88";
    /**
     * 自定义token, 用作生成签名,从而验证安全性
     */
    public static final String TOKEN = "cherry";
    /**
     * 调用微信接口，获取微信access_token（GET） 限200（次/天）
     */
    public final static String ACCESS_TOKEN_INTERFACE_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APP_SECRET";
    /**
     *存放：1.token，2：获取token的时间,3.过期时间
     */
    public final static Map<String,Object> ACCESS_TOKEN_MAP = new HashMap<String,Object>();
    /**
     *调用微信接口，获取用户信息Interface
     */
    public static final String USER_INTERFACE_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    /**
     * 调用微信接口,创建菜单
     */
    public final static String CREATE_MENU_INTERFACE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


}
