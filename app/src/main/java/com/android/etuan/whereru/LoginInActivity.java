package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.etuan.whereru.utils.InterfaceConstant;
import com.android.etuan.whereru.utils.jsonjavabean.Person;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginInActivity extends Activity {

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
                Intent intent = new Intent(LoginInActivity.this, RegisterPageOneActivity.class);
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
                    Toast.makeText(LoginInActivity.this, "请输入账号信息", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("phone", phoneNumber);
                    params.put("password", password);
                    JSONObject jsonObject = new JSONObject(params);
                    JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST,
                            InterfaceConstant.LOGIN_URL, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Person.getInstance().getInfoFromJson(jsonObject);
                                    Intent intent = new Intent(LoginInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                }
                            });
                    MyVolleySingleton.getInstance(LoginInActivity.this.getApplicationContext())
                            .addToRequestQueue(jsonRequest);

                }
            }
        });

    }

}
