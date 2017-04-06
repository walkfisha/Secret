package com.flyingfish.secret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println(MyContacts.getContactsJSONString(this));

        String token = Config.getCachedToken(this);
        String phone_num = Config.getCachedPhoneNum(this);

        if (token != null&&phone_num != null){
            Intent intent = new Intent(this,TimeLineActivity.class);
            intent.putExtra(Config.KEY_TOKEN,token);
            intent.putExtra(Config.KEY_PHONE_NUM,phone_num);
            startActivity(intent);
        }else{
            startActivity(new Intent(this,LoginActivity.class));
        }
        finish();
    }
}
