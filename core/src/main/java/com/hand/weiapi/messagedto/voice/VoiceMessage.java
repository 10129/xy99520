package com.hand.weiapi.messagedto.voice;

import com.hand.weiapi.messagedto.BaseMessage;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class VoiceMessage extends BaseMessage {
    /**
     * 消息id，64位整型
     */
    public Voice Voice ;

    public com.hand.weiapi.messagedto.voice.Voice getVoice() {
        return Voice;
    }

    public void setVoice(com.hand.weiapi.messagedto.voice.Voice voice) {
        Voice = voice;
    }
}
