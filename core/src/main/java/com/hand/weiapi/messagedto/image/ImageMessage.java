package com.hand.weiapi.messagedto.image;

import com.hand.weiapi.messagedto.BaseMessage;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class ImageMessage extends BaseMessage {


    /**
     * 消息id，64位整型
     */
    public Image Image ;
    /**
     * 图片的URL
     */
//    public String  PicUrl;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }

//    public String getPicUrl() {
//        return PicUrl;
//    }
//
//    public void setPicUrl(String picUrl) {
//        PicUrl = picUrl;
//    }
}
