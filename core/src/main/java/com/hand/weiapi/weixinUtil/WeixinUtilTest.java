package com.hand.weiapi.weixinUtil;

import com.hand.weiapi.menu.CommandButton;
import com.hand.weiapi.menu.ComplexButton;
import com.hand.weiapi.menu.Menu;
import com.hand.weiapi.menu.ViewButton;
import net.sf.json.JSONObject;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class WeixinUtilTest {
    /**
     *
     * @author qincd
     * @date Nov 6, 2014 9:57:54 AM
     */
    public static void main(String[] args) {
        // 1).获取access_token
        AccessToken accessToken = WeixinUtil.getAccessToken(WeixinUtil.APPID, WeixinUtil.APP_SECRET);
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
        WeixinUtil.createMenu(menu, accessToken.getToken());
    }
}
