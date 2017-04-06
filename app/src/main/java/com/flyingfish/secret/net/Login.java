package com.flyingfish.secret.net;

import com.flyingfish.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AMOBBS on 2017/3/29.
 */

public class Login {
    public Login(String phone_md5,String code,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null){
                                successCallback.onSuccess(obj.getString(Config.KEY_TOKEN));
                            }
                            break;
                        default:
                            if (failCallback != null){
                                failCallback.onFall();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null){
                        failCallback.onFall();
                    }
                }
            }
        }, new NetConnection.FallCallback() {
            @Override
            public void onFall() {
                if (failCallback != null){
                    failCallback.onFall();
                }
            }
        },Config.KEY_ACTION,Config.ACTION_LOGIN,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_CODE,code);
    }

    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface FailCallback{
        void onFall();
    }
}
