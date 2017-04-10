package com.flyingfish.secret.net;

/**
 * Created by AMOBBS on 2017/4/10.
 */

public class Comments {
    private String content;
    private String phone_md5;

    public Comments(String content, String phone_md5) {
        this.content = content;
        this.phone_md5 = phone_md5;
    }

    public String getContent() {
        return content;
    }

    public String getPhone_md5() {
        return phone_md5;
    }
}
