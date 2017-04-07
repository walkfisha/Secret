package com.flyingfish.secret;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.flyingfish.secret.net.Message;
import com.flyingfish.secret.net.Timeline;
import com.flyingfish.secret.net.UploadContacts;
import com.flyingfish.secret.utils.PhoneMD5;

import org.json.JSONArray;

import java.util.List;

public class TimeLineActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String phone_num,phone_md5,token;
    private MessageListAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        adapter = new MessageListAdapter(this);
        listView = (ListView) findViewById(R.id.contact_list);
        listView.setAdapter(adapter);
        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        phone_md5 = PhoneMD5.md5(phone_num,"jiandan");

        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.conncting),getResources().getString(R.string.connect_server));
        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {
                pd.dismiss();
                loadMessage();
            }
        }, new UploadContacts.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();

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
        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.conncting),getResources().getString(R.string.connect_server));

        new Timeline(phone_md5, token, 1, 20, new Timeline.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<Message> timeline) {
                pd.dismiss();
                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallback() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(TimeLineActivity.this,R.string.fail_to_load_timeline,Toast.LENGTH_LONG).show();
            }
        });
        System.out.println(">>>>>>>>LoadMessage<<<<<<<");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Message msg = adapter.getItem(i);
        Intent intent = new Intent(this,ContactMessageActivity.class);
        intent.putExtra(Config.KEY_MSG,msg.getMsg());
        intent.putExtra(Config.KEY_MSG_ID,msg.getMsgId());
        intent.putExtra(Config.KEY_PHONE_MD5,phone_md5);
        startActivity(intent);
    }
}
