package com.hand.weiapi.messagedto.text;

/**
 * Created by xieshuai on 2018/1/18.
 */

import com.hand.weiapi.messagedto.BaseMessage;

/**
 * 文本消息
 * @author Zhangsy
 *
 */
public class TextMessage extends BaseMessage {

    /**
     * 文本消息内容
     */
    private String Content;
    /**
     * 消息id，64位整型
     */
    private long MsgId ;

    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
    public long getMsgId() {
        return MsgId;
    }
    public void setMsgId(long msgId) {
        MsgId = msgId;
    }

}