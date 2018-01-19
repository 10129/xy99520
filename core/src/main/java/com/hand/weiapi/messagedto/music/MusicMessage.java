package com.hand.weiapi.messagedto.music;

import com.hand.weiapi.messagedto.BaseMessage;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class MusicMessage extends BaseMessage {
    /**
     * 消息id，64位整型
     */
    private Music Music ;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
