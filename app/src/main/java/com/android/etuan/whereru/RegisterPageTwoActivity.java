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
import android.widget.Toast;

import com.android.etuan.whereru.utils.InterfaceConstant;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    //默认男
    private String mSexChooseResult = "男", mSchoolName;

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

                Map<String, String> params = new HashMap<>();
                params.put("name", userName);
                params.put("password", password);
                params.put("phone", phoneNumber);
                params.put("sex", mSexChooseResult);
                params.put("school", mSchoolName);

                JSONObject jsonObject = new JSONObject(params);
                JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                        InterfaceConstant.REGISTER_URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Toast.makeText(RegisterPageTwoActivity.this,"注册成功！"
                                        ,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterPageTwoActivity.this,
                                        LoginInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(RegisterPageTwoActivity.this,
                                        "注册失败：" + volleyError.toString()
                                        , Toast.LENGTH_LONG).show();
                            }
                        });
                MyVolleySingleton.getInstance(RegisterPageTwoActivity.this.getApplicationContext())
                        .addToRequestQueue(jsonRequest);

            }
        });

        //从 注册界面2 跳转到 登陆界面
        mSignInTextViewInRegisterPageTwo = (TextView)
                findViewById(R.id.sign_in_text_view_in_register_page_two);
        mSignInTextViewInRegisterPageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageTwoActivity.this, LoginInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
