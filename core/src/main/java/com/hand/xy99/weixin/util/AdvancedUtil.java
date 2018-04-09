package com.hand.xy99.weixin.util;

import com.hand.xy99.weixin.pojo.AccessTokenInfo;
import com.hand.xy99.weixin.pojo.SNSUserInfo;
import com.hand.xy99.weixin.pojo.UserInfo;
import com.hand.xy99.weixin.pojo.WeixinOauth2Token;
import com.hand.xy99.weixin.util.common.CommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hand on 2018/1/21.
 */
public class AdvancedUtil {
    static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
    // 获取网页授权地址
    public final static String OAURH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户授权地址
    public final static  String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    /**
     * 获取网页授权凭证
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = OAURH2_URL;
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = USERINFO_URL;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }
    /**
     * 获取公众号关注的用户openid
     * @return
     */
    public List<String> getUserOpenId(String access_token, String nextOpenid)
    {
        String path = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        path = path.replace("ACCESS_TOKEN", access_token).replace("NEXT_OPENID", nextOpenid);
        System.out.println("path:" + path);

        List<String> result = null;
        try
        {
            String strResp = WeChatUtil.doHttpsGet(path, "");
            System.out.println(strResp);

            Map map = WeChatUtil.jsonToMap(strResp);
            Map tmapMap = (Map) map.get("data");

            result = (List<String>) tmapMap.get("openid");

            System.out.println(result.toString());

        }
        catch (HttpException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 通过用户openid 获取用户信息
     * @param userOpenids
     * @return
     */
    public List<UserInfo> getUserInfo(List<String> userOpenids)
    {
        // 1、获取access_token
        // 使用测试 wx9015ccbcccf8d2f5 02e3a6877fa5fdeadd78d0f6f3048245
//        WeChatTokenService tWeChatTokenService = new WeChatTokenService();
        String tAccess_Token = AccessTokenInfo.accessToken.getAccessToken();

        // 2、封装请求数据
        List user_list = new ArrayList<Map>();
        for (int i = 0; i < userOpenids.size(); i++)
        {
            String openid = userOpenids.get(i);
            Map tUserMap = new HashMap<String, String>();
            tUserMap.put("openid", openid);
            tUserMap.put("lang", "zh_CN");
            user_list.add(tUserMap);
        }
        System.out.println(user_list.toString());
        Map requestMap = new HashMap<String, List>();
        requestMap.put("user_list", user_list);
        String tUserJSON = JSONObject.fromObject(requestMap).toString();

        // 3、请求调用
        String result = getUserInfobyHttps(tAccess_Token, tUserJSON);
        System.out.println(result);

        // 4、解析返回将结果
        return parseUserInfo(result);
    }

    /**
     * 解析返回用户信息数据
     * @param userInfoJSON
     * @return
     */
    private List<UserInfo> parseUserInfo(String userInfoJSON)
    {
        List user_info_list = new ArrayList<UserInfo>();

        Map tMapData = WeChatUtil.jsonToMap(userInfoJSON);

        List<Map> tUserMaps = (List<Map>) tMapData.get("user_info_list");

        for (int i = 0; i < tUserMaps.size(); i++)
        {
            UserInfo tUserInfo = new UserInfo();
            tUserInfo.setSubscribe((Integer) tUserMaps.get(i).get("subscribe"));
            tUserInfo.setSex((Integer) tUserMaps.get(i).get("sex"));
            tUserInfo.setOpenId((String) tUserMaps.get(i).get("openid"));
            tUserInfo.setNickname((String) tUserMaps.get(i).get("nickname"));
            tUserInfo.setLanguage((String) tUserMaps.get(i).get("language"));
            tUserInfo.setCity((String) tUserMaps.get(i).get("city"));
            tUserInfo.setProvince((String) tUserMaps.get(i).get("province"));
            tUserInfo.setCountry((String) tUserMaps.get(i).get("country"));
            tUserInfo.setHeadimgurl((String) tUserMaps.get(i).get("headimgurl"));
            tUserInfo.setSubscribetime((Integer) tUserMaps.get(i).get("subscribe_time"));
            tUserInfo.setRemark((String) tUserMaps.get(i).get("remark"));
            tUserInfo.setGroupid((Integer) tUserMaps.get(i).get("groupid"));
            user_info_list.add(tUserInfo);
        }

        return user_info_list;
    }

    /**
     * 调用HTTPS接口，获取用户详细信息
     * @param access_token
     * @param requestData
     * @return
     */
    private String getUserInfobyHttps(String access_token, String requestData)
    {
        // 返回报文
        String strResp = "";
        String path = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
        path = path.replace("ACCESS_TOKEN", access_token);

        try
        {
            strResp = WeChatUtil.doHttpsPost(path, requestData);
        }
        catch (HttpException e)
        {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!" + e);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // 发生网络异常
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {}
        return strResp;
    }
}
