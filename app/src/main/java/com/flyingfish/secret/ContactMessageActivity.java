package com.flyingfish.secret;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingfish.secret.net.Comments;
import com.flyingfish.secret.net.GetComment;

import java.util.List;

public class ContactMessageActivity extends AppCompatActivity {

    private String phone_md5,msg,msgId,token;
    private TextView msgContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_message);

        msgContent = (TextView) findViewById(R.id.contact_message);

        Intent data = getIntent();
        phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
        msg = data.getStringExtra(Config.KEY_MSG);
        msgId = data.getStringExtra(Config.KEY_MSG_ID);
        token = data.getStringExtra(Config.KEY_TOKEN);
        msgContent.setText(msg);

        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.conncting),getResources().getString(R.string.connect_server));
        new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallback() {
            @Override
            public void onSuccess(String msgId, int page, int perpage, List<Comments> comments) {
                pd.dismiss();
            }
        }, new GetComment.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if (errorCode == Config.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(ContactMessageActivity.this,LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(ContactMessageActivity.this,"评论获取失败， 请重试",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
