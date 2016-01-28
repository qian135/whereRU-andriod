package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zhang on 1/19/2016.
 */
public class SignInActivity extends Activity {

    private TextView mRegisterTextView;

    private Button mSignInButtonInSignInPage;

    //登录页 手机号,密码 输入框
    private EditText mSignInPhoneNumberEditText,mSignInPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in);

        //登录页 手机号,密码 输入框
        mSignInPhoneNumberEditText = (EditText)
                findViewById(R.id.sign_in_phone_number_edit_text);
        mSignInPasswordEditText = (EditText) findViewById(R.id.sign_in_password_edit_text);


        //跳转到其他界面,监听输入的数据的代码

        mRegisterTextView = (TextView) findViewById(R.id.register_text_view);
        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,RegisterPageOneActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSignInButtonInSignInPage = (Button) findViewById(R.id.sign_in_button_in_sign_in_page);
        mSignInButtonInSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                startActivity(intent);

                String phoneNumber = mSignInPhoneNumberEditText.getText().toString();
                String password = mSignInPasswordEditText.getText().toString();

                System.out.println(phoneNumber + " phoneNumber come from SignInActivity ");
                System.out.println(password + " password come from SignInActivity ");

                finish();
            }
        });

    }

}
