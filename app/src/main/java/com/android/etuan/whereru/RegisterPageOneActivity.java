package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.etuan.whereru.utils.httputil.HttpGetSchools;
import com.android.etuan.whereru.utils.jsonutil.JSONSchools;

import java.util.ArrayList;

/**
 * Created by zhang on 1/28/2016.
 */
public class RegisterPageOneActivity extends Activity {

    //自定义的下拉列表组件类
    private CustomDropDownListView mChooseSchoolCustomDropDownListView;

    //在注册界面1的登陆TextView，点击返回登陆界面
    private TextView mSignInTextViewInRegisterPageOne;

    //在注册界面1的下一步TextView，点击跳转到注册界面2
    private TextView mNextStepTextViewInRegisterPageOne,mSchoolNameTextView;

    //学校列表数据
    private ArrayList<String> mSchoolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_one);

        //下拉列表的数据
        mSchoolList = new ArrayList<>();

        HttpGetSchools.setContext(RegisterPageOneActivity.this);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x234) {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("data");
                    mSchoolList = JSONSchools.getSchoolsFromJson(result);
                    mChooseSchoolCustomDropDownListView = (CustomDropDownListView)
                            findViewById(R.id.choose_school_custom_drop_down_list_view);
                    mChooseSchoolCustomDropDownListView.setmContext(RegisterPageOneActivity.this);
                    //这样写没用
//        mChooseSchoolCustomDropDownListView = new CustomDropDownListView(this);
                    mChooseSchoolCustomDropDownListView.setItemsData("请选择你的学校", mSchoolList);
                } else {
//                    txt_schools.setText("无法获得学校信息");
                }
                super.handleMessage(msg);
            }
        };
        HttpGetSchools.setHandler(handler);
        HttpGetSchools.getSchools();

        //跳转到登陆界面
        mSignInTextViewInRegisterPageOne = (TextView)
                findViewById(R.id.sign_in_text_view_in_register_page_one);
        mSignInTextViewInRegisterPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageOneActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSchoolNameTextView = (TextView) findViewById(R.id.school_name_text_view);
        //跳转到注册界面2
        mNextStepTextViewInRegisterPageOne = (TextView)
                findViewById(R.id.next_step_text_view_in_register_page_one);
        mNextStepTextViewInRegisterPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSchoolNameTextView.getText().toString() != "请选择你的学校") {
                    Intent intent = new
                            Intent(RegisterPageOneActivity.this, RegisterPageTwoActivity.class);
                    intent.putExtra("schoolName",mSchoolNameTextView.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterPageOneActivity.this,
                            "请选择你的学校", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
