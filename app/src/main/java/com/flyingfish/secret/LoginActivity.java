package com.flyingfish.secret;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flyingfish.secret.net.GetCode;
import com.flyingfish.secret.net.Login;
import com.flyingfish.secret.utils.PhoneMD5;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhone = null,etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = (EditText) findViewById(R.id.userphoneEdit);
        etCode = (EditText) findViewById(R.id.passwordEdit);
        findViewById(R.id.bt_get_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etPhone.getText())){
                    Toast.makeText(LoginActivity.this, R.string.phone_num_can_not_be_empty,Toast.LENGTH_LONG).show();

                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(LoginActivity.this,getString(R.string.conncting),getString(R.string.connect_server));
                new GetCode(etPhone.getText().toString(), new GetCode.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.success_to_get_code,Toast.LENGTH_LONG).show();
                    }
                }, new GetCode.FallCallback() {
                    @Override
                    public void onFall() {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.fail_to_get_code,Toast.LENGTH_LONG).show();
                    }
                });
            }

        });

        findViewById(R.id.logininBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etPhone.getText())){
                    Toast.makeText(LoginActivity.this, R.string.phone_num_can_not_be_empty,Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(etCode.getText())){
                    Toast.makeText(LoginActivity.this,R.string.code_can_not_be_empty,Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(LoginActivity.this,getString(R.string.conncting),getString(R.string.connect_server));
                new Login(PhoneMD5.md5(etPhone.getText().toString(),"jiandan"), etCode.getText().toString(), new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String token) {
                        pd.dismiss();
                        Config.cacheToken(LoginActivity.this,token);
                        Config.cachePhoneNum(LoginActivity.this,etPhone.getText().toString());

                        Intent intent = new Intent(LoginActivity.this,TimeLineActivity.class);
                        intent.putExtra(Config.KEY_TOKEN,token);
                        intent.putExtra(Config.KEY_PHONE_NUM,etPhone.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                }, new Login.FailCallback() {
                    @Override
                    public void onFall() {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,R.string.fail_to_login,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}
