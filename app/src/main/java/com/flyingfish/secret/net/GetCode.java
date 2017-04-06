package com.flyingfish.secret.net;

import com.flyingfish.secret.Config;
import com.flyingfish.secret.net.HttpMethod;
import com.flyingfish.secret.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by AMOBBS on 2017/3/14.
 * 获取验证码的通信类的实现
 */

public class GetCode {
    public GetCode(String phone, final SuccessCallback successCallback, final FallCallback fallCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    switch (jsonObj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null){
                                successCallback.onSuccess();
                            }
                            break;
                        default:
                            if (fallCallback != null){
                                fallCallback.onFall();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (fallCallback != null){
                        fallCallback.onFall();
                    }
                }
            }
        }, new NetConnection.FallCallback() {
            @Override
            public void onFall() {
                if (fallCallback != null){
                    fallCallback.onFall();
                }
            }
        },Config.KEY_ACTION,Config.ACTION_GET_CODE,Config.KEY_PHONE_NUM,phone);
    }

    public interface SuccessCallback{
        void onSuccess();
    }

    public interface FallCallback{
        void onFall();
    }
}
