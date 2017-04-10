package com.flyingfish.secret.net;

/**
 * Created by AMOBBS on 2017/4/10.
 */

import com.flyingfish.secret.Config;
import com.flyingfish.secret.ContactMessageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AMOBBS
 * @description 获取消息评论的通信类
 * @time on 2017/4/10 10:52
 */
public class GetComment {
    public GetComment(String phone_md5, String token, String msgId, int page, int perpage, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null){

                                List<Comments> comments = new ArrayList<Comments>();
                                JSONArray commentJSONArray = obj.getJSONArray(Config.KEY_COMMENTS);
                                JSONObject commentObj;
                                for (int i = 0;i<commentJSONArray.length();i++){
                                    commentObj = commentJSONArray.getJSONObject(i);
                                    comments.add(new Comments(commentObj.getString(Config.KEY_CONTENT),commentObj.getString(Config.KEY_PHONE_MD5)));
                                }
                                successCallback.onSuccess(obj.getString(Config.KEY_MSG_ID),obj.getInt(Config.KEY_PAGE),obj.getInt(Config.KEY_PERPAGE),comments);
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallback != null){
                                failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (failCallback != null){
                                failCallback.onFail(Config.RESULT_STATUS_FALL);
                            }
                            break;
                    }
                } catch (JSONException e) {
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
        },Config.KEY_ACTION,Config.ACTION_GET_COMMENT,
                Config.KEY_TOKEN,token,
                Config.KEY_MSG_ID, msgId,
                Config.KEY_PAGE,page+"",
                Config.KEY_PERPAGE,perpage+"");
    }



    public static interface SuccessCallback{
        void onSuccess(String msgId, int page, int perpage, List<Comments> comments);
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
