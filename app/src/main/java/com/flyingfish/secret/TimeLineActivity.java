package com.flyingfish.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyingfish.secret.net.UploadContacts;
import com.flyingfish.secret.utils.PhoneMD5;

public class TimeLineActivity extends AppCompatActivity {

    private String phone_num,phone_md5,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        phone_md5 = PhoneMD5.md5(phone_num,"jiandan");

        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {
                loadMessage();
            }
        }, new UploadContacts.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(TimeLineActivity.this,LoginActivity.class));
                    finish();
                }else {
                    loadMessage();
                }
            }
        });
    }

    private void loadMessage(){
        System.out.println(">>>>>>>>LoadMessage<<<<<<<");
    }
}
