package com.hand.xy99.weiapi.weChatServlet;

import com.hand.xy99.weiapi.dto.AccessToken;
import com.hand.xy99.weiapi.dto.AccessTokenInfo;
import com.hand.xy99.weiapi.menu.CommandButton;
import com.hand.xy99.weiapi.menu.ComplexButton;
import com.hand.xy99.weiapi.menu.Menu;
import com.hand.xy99.weiapi.menu.ViewButton;
import com.hand.xy99.weiapi.weixinUtil.MyX509TrustManager;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AccessTokenServlet extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(AccessTokenServlet.class);
    public final static String APPID = "wxd135f4cb0d7346bf";
    public final static String APP_SECRET = "7e55c0ed3b6e16d4ea36c7365f410d88";
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APP_SECRET";
    // 创建菜单
    public final static String create_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 存放：1.token，2：获取token的时间,3.过期时间
    public final static Map<String,Object> accessTokenMap = new HashMap<String,Object>();
    /**
     * 获取AccessToken以线程的方式运行
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        System.out.println("-----启动AccessTokenServlet-----");
        super.init();

//        final String appId = "wxd135f4cb0d7346bf";//getInitParameter("appId");
//        final String appSecret = "7e55c0ed3b6e16d4ea36c7365f410d88";//getInitParameter("appSecret");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //获取accessToken
                        AccessTokenInfo.accessToken = getAccessToken(APPID, APP_SECRET);
                        //获取成功
                        if (AccessTokenInfo.accessToken != null) {
                            createmenu2();
                            //获取到access_token 休眠7000秒,大约2个小时左右
                            Thread.sleep(7000 * 1000);
                        } else {
                            //获取失败
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
    public static AccessToken getAccessToken(String appid, String appSecret) {
        AccessToken at = new AccessToken();
        // 每次获取access_token时，先从accessTokenMap获取，如果过期了就重新从微信获取
        // 没有过期直接返回
        // 从微信获取的token的有效期为2个小时
        if (!accessTokenMap.isEmpty()) {
            Date getTokenTime = (Date) accessTokenMap.get("getTokenTime");
            Calendar c = Calendar.getInstance();
            c.setTime(getTokenTime);
            c.add(Calendar.HOUR_OF_DAY, 2);

            getTokenTime = c.getTime();
            if (getTokenTime.after(new Date())) {
                logger.info("缓存中发现token未过期，直接从缓存中获取access_token");
                // token未过期，直接从缓存获取返回
                String token = (String) accessTokenMap.get("token");
                Integer expire = (Integer) accessTokenMap.get("expire");
                at.setToken(token);
                at.setExpiresIn(expire);
                return at;
            }
        }
        String requestUrl = access_token_url.replace("APPID", appid).replace("APP_SECRET", appSecret);

        JSONObject object = handleRequest(requestUrl, "GET", null);
        String access_token = object.getString("access_token");
        int expires_in = object.getInt("expires_in");

        logger.info("\naccess_token:" + access_token);
        logger.info("\nexpires_in:" + expires_in);

        at.setToken(access_token);
        at.setExpiresIn(expires_in);

        // 每次获取access_token后，存入accessTokenMap
        // 下次获取时，如果没有过期直接从accessTokenMap取。
        accessTokenMap.put("getTokenTime", new Date());
        accessTokenMap.put("token", access_token);
        accessTokenMap.put("expire", expires_in);

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
        String requestUrl = AccessTokenServlet.create_menu_url.replace("ACCESS_TOKEN", accessToken);
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
    public static boolean createmenu2(){
        // 1).获取access_token
        AccessToken accessToken = AccessTokenInfo.accessToken;
        // 2).创建菜单
        Menu menu = new Menu();

        // 菜单1
        ComplexButton cb0 = new ComplexButton();
        cb0.setName("超值预定");

        ViewButton cb01 = new ViewButton();
        cb01.setName("团购订单");
        cb01.setType("view");
        cb01.setUrl("http://www.meituan.com");

        ViewButton cb02 = new ViewButton();
        cb02.setName("微信团购");
        cb02.setType("view");
        cb02.setUrl("http://www.weixin.com");

        cb0.setSub_button(new ViewButton[]{cb01,cb02});

        // 菜单2
        ComplexButton cb1 = new ComplexButton();
        cb1.setName("我的服务");

        ViewButton cb11 = new ViewButton();
        cb11.setName("办登机牌");
        cb11.setType("view");
        cb11.setUrl("http://www.meituan.com");

        ViewButton cb12 = new ViewButton();
        cb12.setName("航班动态");
        cb12.setType("view");
        cb12.setUrl("http://www.meituan.com");

        ViewButton cb13 = new ViewButton();
        cb13.setName("里程查询");
        cb13.setType("view");
        cb13.setUrl("http://www.meituan.com");

        cb1.setSub_button(new ViewButton[]{cb11,cb12,cb13});

        // 菜单3
        ComplexButton cb2 = new ComplexButton();
        cb2.setName("我的测试");

        CommandButton cb21 = new CommandButton();
        cb21.setName("回复文字");
        cb21.setType("click");
        cb21.setKey("reply_words");

        CommandButton cb22 = new CommandButton();
        cb22.setName("回复音乐");
        cb22.setType("click");
        cb22.setKey("reply_music");

        CommandButton cb23 = new CommandButton();
        cb23.setName("回复图文");
        cb23.setType("click");
        cb23.setKey("reply_news");

        CommandButton cb24 = new CommandButton();
        cb24.setName("回复链接");
        cb24.setType("click");
        cb24.setKey("reply_link");

        cb2.setSub_button(new CommandButton[]{cb21,cb22,cb23,cb24});

        menu.setButton(new ComplexButton[]{cb0,cb1,cb2});
        String menuJsonString = JSONObject.fromObject(menu).toString();
        System.out.println(menuJsonString);
        Boolean aaa= createMenu(menu, accessToken.getToken());
        return aaa;
    }
}
