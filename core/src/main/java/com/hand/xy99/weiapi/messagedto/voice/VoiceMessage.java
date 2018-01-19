package com.hand.xy99.weiapi.messagedto.voice;

import com.hand.xy99.weiapi.messagedto.BaseMessage;

/**
 * Created by xieshuai on 2018/1/18.
 */
public class VoiceMessage extends BaseMessage {
    /**
     * 消息id，64位整型
     */
    private Voice Voice ;

    public com.hand.xy99.weiapi.messagedto.voice.Voice getVoice() {
        return Voice;
    }

    public void setVoice(com.hand.xy99.weiapi.messagedto.voice.Voice voice) {
        Voice = voice;
    }
}
