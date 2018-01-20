package com.hand.xy99.weiapi.util;

import com.hand.xy99.weiapi.dto.AccessToken;
import com.hand.xy99.weiapi.dto.AccessTokenInfo;
import com.hand.xy99.weiapi.menu.CommandButton;
import com.hand.xy99.weiapi.menu.ComplexButton;
import com.hand.xy99.weiapi.menu.Menu;
import com.hand.xy99.weiapi.menu.ViewButton;
import com.hand.xy99.weiapi.weChatServlet.AccessTokenServlet;
import com.hand.xy99.weiapi.weixinUtil.MyX509TrustManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Created by hand on 2018/1/20.
 */
public class MenuUtil {
    static Logger logger = LoggerFactory.getLogger(MenuUtil.class);
   

}
