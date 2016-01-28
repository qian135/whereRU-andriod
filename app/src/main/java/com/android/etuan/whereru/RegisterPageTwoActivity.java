package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by zhang on 1/28/2016.
 */
public class RegisterPageTwoActivity extends Activity {

    //注册界面的最终注册按钮
    private Button mRegisterButtonInRegisterPageTwo;

    //在注册界面2的登陆TextView，点击返回登陆界面
    private TextView mSignInTextViewInRegisterPageTwo;

    private EditText mRegisterPageTwoNameEditText,mRegisterPageTwoPasswordEditText,
            mRegisterPageTwoPhoneNumberEditText,mRegisterPageTwoIdentificationCodeEditText;

    private RadioGroup mSexChooseRadioGroup;

    private RadioButton mSexChooseManRadioButton,mSexChooseWomanRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_two);

        mRegisterPageTwoNameEditText = (EditText)
                findViewById(R.id.register_page_two_name_edit_text);
        mRegisterPageTwoPasswordEditText = (EditText)
                findViewById(R.id.register_page_two_password_edit_text);
        mRegisterPageTwoPhoneNumberEditText = (EditText)
                findViewById(R.id.register_page_two_phone_number_edit_text);
        mRegisterPageTwoIdentificationCodeEditText = (EditText)
                findViewById(R.id.register_page_two_identification_code_edit_text);


        //注册成功后从 注册界面2 跳转到 登陆界面 进行登陆（自动填写登陆所需信息）
        mRegisterButtonInRegisterPageTwo = (Button)
                findViewById(R.id.register_button_in_register_page_two);
        mRegisterButtonInRegisterPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageTwoActivity.this,SignInActivity.class);
                startActivity(intent);
                String userName = mRegisterPageTwoNameEditText.getText().toString();
                String password = mRegisterPageTwoPasswordEditText.getText().toString();
                String phoneNumber = mRegisterPageTwoPhoneNumberEditText.getText().toString();

                System.out.println(userName + " phoneNumber come from RegisterPageTwoActivity ");
                System.out.println(phoneNumber + " phoneNumber come from RegisterPageTwoActivity ");
                System.out.println(password + " password come from RegisterPageTwoActivity ");

                finish();
            }
        });

        //从 注册界面2 跳转到 登陆界面
        mSignInTextViewInRegisterPageTwo = (TextView)
                findViewById(R.id.sign_in_text_view_in_register_page_two);
        mSignInTextViewInRegisterPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageTwoActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
