package com.hand.weiapi.weixinUtil;

/**
 * Created by xieshuai on 2018/1/18.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.hand.weiapi.menu.Menu;
import net.sf.json.JSONObject;
import com.hand.weiapi.weChatServlet.AccessTokenServlet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinUtil {
    static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    public final static String APPID = "****************";
    public final static String APP_SECRET = "************************";
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    // 创建菜单
    public final static String create_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 存放：1.token，2：获取token的时间,3.过期时间
    public final static Map<String,Object> accessTokenMap = new HashMap<String,Object>();
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
            log.error("URL错误！");
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
     * 获取access_token
     *
     * @author qincd
     * @date Nov 6, 2014 9:56:43 AM
     */
    public static AccessToken getAccessToken(String appid,String appSecret) {
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
                log.info("缓存中发现token未过期，直接从缓存中获取access_token");
                // token未过期，直接从缓存获取返回
                String token = (String) accessTokenMap.get("token");
                Integer expire = (Integer) accessTokenMap.get("expire");
                at.setToken(token);
                at.setExpiresIn(expire);
                return at;
            }
        }
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appSecret);

        JSONObject object = handleRequest(requestUrl, "GET", null);
        String access_token = object.getString("access_token");
        int expires_in = object.getInt("expires_in");

        log.info("\naccess_token:" + access_token);
        log.info("\nexpires_in:" + expires_in);

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
     * 创建菜单
     *
     * @author qincd
     * @date Nov 6, 2014 9:56:36 AM
     */
    public static boolean createMenu(Menu menu, String accessToken) {
        String requestUrl = create_menu_url.replace("ACCESS_TOKEN", accessToken);
        String menuJsonString = JSONObject.fromObject(menu).toString();
        JSONObject jsonObject = handleRequest(requestUrl, "POST", menuJsonString);
        String errorCode = jsonObject.getString("errcode");
        if (!"0".equals(errorCode)) {
            log.error(String.format("菜单创建失败！errorCode:%d,errorMsg:%s",jsonObject.getInt("errcode"),jsonObject.getString("errmsg")));
            return false;
        }

        log.info("菜单创建成功！");

        return true;
    }
}

