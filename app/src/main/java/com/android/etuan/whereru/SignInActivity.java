package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.etuan.whereru.utils.httputil.HttpLogin;
import com.loopj.android.http.RequestParams;

public class SignInActivity extends Activity {

    private TextView mRegisterTextView;

    private Button mSignInButtonInSignInPage;

    //登录页 手机号,密码 输入框
    private EditText mSignInPhoneNumberEditText, mSignInPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in);

        //登录页 手机号,密码 输入框
        mSignInPhoneNumberEditText = (EditText)
                findViewById(R.id.sign_in_phone_number_edit_text);
        mSignInPasswordEditText = (EditText) findViewById(R.id.sign_in_password_edit_text);


        //跳转到其他界面,监听输入的数据的代码

        //跳转到注册界面1
        mRegisterTextView = (TextView) findViewById(R.id.register_text_view);
        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterPageOneActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSignInButtonInSignInPage = (Button) findViewById(R.id.sign_in_button_in_sign_in_page);
        mSignInButtonInSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = mSignInPhoneNumberEditText.getText().toString();
                String password = mSignInPasswordEditText.getText().toString();

                //调用登陆接口进行登陆
                if (phoneNumber.equals("") || password.equals("")) {
                    Toast.makeText(SignInActivity.this, "请输入账号信息", Toast.LENGTH_SHORT).show();
                } else {
                    RequestParams params = new RequestParams();
                    params.add("phone", phoneNumber);
                    params.add("password", password);
                    HttpLogin.setContext(SignInActivity.this);
                    Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == 0x234) {
                                //登陆成功跳转到首页
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                //登录失败
                            }
                            super.handleMessage(msg);
                        }
                    };
                    HttpLogin.setHandler(handler);
                    HttpLogin.Login(params);
                }
            }
        });

    }

}
