package com.hand.xy99.weixin.weChatServlet;

import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weixin.pojo.message.menu.Menu;
import com.hand.xy99.weixin.pojo.Token;
import com.hand.xy99.weixin.pojo.WeixinUserInfo;
import com.hand.xy99.weixin.service.IWeixinService;
import com.hand.xy99.weixin.service.impl.WeixinServiceImpl;
import com.hand.xy99.weixin.util.common.CommonUtil;
import com.hand.xy99.weixin.util.common.MyX509TrustManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.*;


public class AccessTokenServlet extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(AccessTokenServlet.class);

    /**
     * 获取AccessToken以线程的方式运行
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        System.out.println("-----启动AccessTokenServlet-----");
        super.init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //获取accessToken
                        AccessTokenInfo.accessToken = getAccessToken(CommonUtil.APPID, CommonUtil.APP_SECRET);
                        //获取成功
                        if (AccessTokenInfo.accessToken != null) {
                            //创建菜单
                            IWeixinService weixinService=new WeixinServiceImpl();
                            Menu menu=weixinService.getMenu(AccessTokenInfo.accessToken);
                            String token= AccessTokenInfo.accessToken.getAccessToken();
                            Boolean bl=createMenu(menu,  token);
                            logger.info("成功创建菜单！");
                            //获取到access_token 休眠7000秒,大约2个小时左右
                            System.out.println("-----获取到access_token 休眠7000秒,大约2个小时左右-----");
                            Thread.sleep(7000 * 1000);
                        } else {
                            //获取失败
                            System.out.println("-----获取到access_token失败 休眠2秒左右-----");
                            Thread.sleep(1000 * 3); //获取的access_token为空 休眠3秒
                        }
                    } catch (Exception e) {
                        System.out.println("发生异常：" + e.getMessage());
                        e.printStackTrace();
                        try {
                            Thread.sleep(1000 * 10); //发生异常休眠1秒
                        } catch (Exception e1) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 获取access_token
     *
     * @author qincd
     * @date Nov 6, 2014 9:56:43 AM
     */
    public static Token getAccessToken(String appid, String appSecret) {
        Token at = new Token();
        // 每次获取access_token时，先从accessTokenMap获取，如果过期了就重新从微信获取
        // 没有过期直接返回
        // 从微信获取的token的有效期为2个小时
        if (!CommonUtil.accessTokenMap.isEmpty()) {
            Date getTokenTime = (Date) CommonUtil.accessTokenMap.get("getTokenTime");
            Calendar c = Calendar.getInstance();
            c.setTime(getTokenTime);
            c.add(Calendar.HOUR_OF_DAY, 2);

            getTokenTime = c.getTime();
            if (getTokenTime.after(new Date())) {
                logger.info("缓存中发现token未过期，直接从缓存中获取access_token");
                // token未过期，直接从缓存获取返回
                String token = (String) CommonUtil.accessTokenMap.get("token");
                Integer expire = (Integer) CommonUtil.accessTokenMap.get("expire");
                at.setAccessToken(token);
                at.setExpiresIn(expire);
                return at;
            }
        }
        String requestUrl = CommonUtil.access_token_url.replace("APPID", appid).replace("APP_SECRET", appSecret);

        JSONObject object = handleRequest(requestUrl, "GET", null);
        String access_token = object.getString("access_token");
        int expires_in = object.getInt("expires_in");

        logger.info("\naccess_token:" + access_token);
        logger.info("\nexpires_in:" + expires_in);

        at.setAccessToken(access_token);
        at.setExpiresIn(expires_in);

        // 每次获取access_token后，存入accessTokenMap
        // 下次获取时，如果没有过期直接从accessTokenMap取。
        AccessTokenInfo.accessToken=at;
        CommonUtil.accessTokenMap.put("getTokenTime", new Date());
        CommonUtil.accessTokenMap.put("token", access_token);
        CommonUtil.accessTokenMap.put("expire", expires_in);

        return at;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject handleRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;

        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            SSLContext ctx = SSLContext.getInstance("SSL", "SunJSSE");
            TrustManager[] tm = {new MyX509TrustManager()};
            ctx.init(null, tm, new SecureRandom());
            SSLSocketFactory sf = ctx.getSocketFactory();
            conn.setSSLSocketFactory(sf);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setUseCaches(false);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                conn.connect();
            }

            if (StringUtils.isNotEmpty(outputStr)) {
                OutputStream out = conn.getOutputStream();
                out.write(outputStr.getBytes("utf-8"));
                out.close();
            }

            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = null;

            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }

            in.close();
            conn.disconnect();

            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("URL错误！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    /**
     * 创建菜单
     *
     * @author qincd
     * @date Nov 6, 2014 9:56:36 AM
     */
    public static boolean createMenu(Menu menu, String accessToken) {
        String requestUrl = CommonUtil.create_menu_url.replace("ACCESS_TOKEN", accessToken);
        String menuJsonString = JSONObject.fromObject(menu).toString();
        JSONObject jsonObject = handleRequest(requestUrl, "POST", menuJsonString);
        String errorCode = jsonObject.getString("errcode");
        if (!"0".equals(errorCode)) {
            logger.error(String.format("菜单创建失败！errorCode:%d,errorMsg:%s",jsonObject.getInt("errcode"),jsonObject.getString("errmsg")));
            return false;
        }

        logger.info("菜单创建成功！");

        return true;
    }
    /**
     * 获取用户信息
     *
     * @param accessToken 接口访问凭证
     * @param openId 用户标识
     * @return WeixinUserInfo
     */
    public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    logger.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    logger.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }

}
