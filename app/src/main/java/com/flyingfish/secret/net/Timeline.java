package com.flyingfish.secret.net;

import com.flyingfish.secret.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMOBBS on 2017/3/31.
 */

public class Timeline {
    public Timeline(String phone_md5, String token, int page, int perpage, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null){
                                List<Message> msgs = new ArrayList<Message>();
                                JSONArray msgsJSONArray = obj.getJSONArray(Config.KEY_TIMELINE);
                                JSONObject msgObj;
                                for (int i = 0;i < msgsJSONArray.length();i++){
                                    msgObj = msgsJSONArray.getJSONObject(i);
                                    msgs.add(new Message(
                                            msgObj.getString(Config.KEY_MSG_ID),
                                            msgObj.getString(Config.KEY_MSG),
                                            msgObj.getString(Config.KEY_PHONE_MD5)));
                                }
                                successCallback.onSuccess(obj.getInt(Config.KEY_PAGE),obj.getInt(Config.KEY_PERPAGE),msgs);
                            }
                            break;
                        default:
                            if (failCallback != null){
                                failCallback.onFail();
                            }
                            break;
                    }
                } catch (JSONException e) {
                    if (failCallback != null){
                        failCallback.onFail();
                    }
                }
            }
        }, new NetConnection.FallCallback() {
            @Override
            public void onFall() {
                if (failCallback != null){
                    failCallback.onFail();
                }
            }
        },Config.KEY_ACTION,Config.ACTION_TIMELINE,
                Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,
                Config.KEY_PAGE,page+"",
                Config.KEY_PERPAGE,perpage+"");
    }

    public static interface SuccessCallback{
        void onSuccess(int page, int perpage, List<Message> timeline);
    }

    public static interface FailCallback{
        void onFail();
    }
}
