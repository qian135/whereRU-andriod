package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.etuan.whereru.utils.httputil.HttpRegister;
import com.loopj.android.http.RequestParams;

/**
 * 注册界面2代码
 */

public class RegisterPageTwoActivity extends Activity {

    //注册界面的最终注册按钮
    private Button mRegisterButtonInRegisterPageTwo;

    //在注册界面2的登陆TextView，点击返回登陆界面
    private TextView mSignInTextViewInRegisterPageTwo;

    private EditText mRegisterPageTwoNameEditText, mRegisterPageTwoPasswordEditText,
            mRegisterPageTwoPhoneNumberEditText;

    private RadioGroup mSexChooseRadioGroup;

    private RadioButton mSexChooseManRadioButton, mSexChooseWomanRadioButton;

    private String mSexChooseResult,mSchoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_two);

        //获取从注册页1传来的学校名
        Intent intent = getIntent();
        mSchoolName = intent.getStringExtra("schoolName");

        mRegisterPageTwoNameEditText = (EditText)
                findViewById(R.id.register_page_two_name_edit_text);
        mRegisterPageTwoPasswordEditText = (EditText)
                findViewById(R.id.register_page_two_password_edit_text);
        mRegisterPageTwoPhoneNumberEditText = (EditText)
                findViewById(R.id.register_page_two_phone_number_edit_text);
//        mRegisterPageTwoIdentificationCodeEditText = (EditText)
//                findViewById(R.id.register_page_two_identification_code_edit_text);
        mSexChooseRadioGroup = (RadioGroup) findViewById(R.id.sex_choose_radio_group);
        mSexChooseManRadioButton = (RadioButton) findViewById(R.id.sex_choose_man_radio_button);
        mSexChooseWomanRadioButton = (RadioButton) findViewById(R.id.sex_choose_woman_radio_button);
        mSexChooseRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mSexChooseManRadioButton.getId()) {
                    mSexChooseResult = "男";
                } else {
                    mSexChooseResult = "女";
                }
            }
        });

        //注册成功后从 注册界面2 跳转到 登陆界面 进行登陆（自动填写登陆所需信息）
        mRegisterButtonInRegisterPageTwo = (Button)
                findViewById(R.id.register_button_in_register_page_two);
        mRegisterButtonInRegisterPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = mRegisterPageTwoNameEditText.getText().toString();
                String password = mRegisterPageTwoPasswordEditText.getText().toString();
                String phoneNumber = mRegisterPageTwoPhoneNumberEditText.getText().toString();

                HttpRegister.setContext(RegisterPageTwoActivity.this);
                RequestParams params = new RequestParams();

                params.add("name", userName);
                params.add("password", password);
                params.add("phone", phoneNumber);
                params.add("sex", mSexChooseResult);
                params.add("school", mSchoolName);

                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == 0x234) {
                            //注册成功
                            Intent intent = new Intent(RegisterPageTwoActivity.this,
                                    SignInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //注册失败
                        }
                        super.handleMessage(msg);
                    }
                };
                HttpRegister.setHandler(handler);
                HttpRegister.Register(params);
            }
        });

        //从 注册界面2 跳转到 登陆界面
        mSignInTextViewInRegisterPageTwo = (TextView)
                findViewById(R.id.sign_in_text_view_in_register_page_two);
        mSignInTextViewInRegisterPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageTwoActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
