package com.hand.xy99.weiapi.messagedto.video;

import com.hand.xy99.weiapi.messagedto.BaseMessage;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class VideoMessage extends BaseMessage {

    /**
     * 消息id，64位整型
     */
    private Video Video ;
    /**
     * 图片的URL
     */
    public com.hand.xy99.weiapi.messagedto.video.Video getVideo() {
        return Video;
    }

    public void setVideo(com.hand.xy99.weiapi.messagedto.video.Video video) {
        Video = video;
    }
}
