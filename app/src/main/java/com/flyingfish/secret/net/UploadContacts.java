package com.flyingfish.secret.net;

import com.flyingfish.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AMOBBS on 2017/3/30.
 */

public class UploadContacts {
    public UploadContacts(String phone_md5, String token, String contacts, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null){
                                successCallback.onSuccess();
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallback != null){
                                failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null){
                        failCallback.onFail(Config.RESULT_STATUS_FALL);
                    }
                }
            }
        }, new NetConnection.FallCallback() {
            @Override
            public void onFall() {
                if (failCallback != null){
                    failCallback.onFail(Config.RESULT_STATUS_FALL);
                }
            }
        },Config.KEY_ACTION,Config.ACTION_UPLOAD_CANTACTS,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_TOKEN,token,Config.KEY_CONTACTS,contacts);
    }

    public static interface SuccessCallback{
        void onSuccess();
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
