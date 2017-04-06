package com.flyingfish.secret;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.flyingfish.secret.utils.PhoneMD5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AMOBBS on 2017/3/30.
 */

public class MyContacts {
    public static String getContactsJSONString(Context context){
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String phoneNum;

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        while (cursor.moveToNext()){
            phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            jsonObject = new JSONObject();
            try {
                jsonObject.put(Config.KEY_PHONE_MD5, PhoneMD5.md5(phoneNum,"jiandan"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
