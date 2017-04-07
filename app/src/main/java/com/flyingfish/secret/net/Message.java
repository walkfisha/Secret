package com.flyingfish.secret.net;

/**
 * Created by AMOBBS on 2017/4/6.
 */

public class Message {
    private String msgId;
    private String msg;
    private String phone_md5;

    public Message(String msgId, String msg, String phone_md5) {
        this.msgId = msgId;
        this.msg = msg;
        this.phone_md5 = phone_md5;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getMsg() {
        return msg;
    }

    public String getPhone_md5() {
        return phone_md5;
    }
}
