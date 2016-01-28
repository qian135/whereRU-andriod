package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    private TextView mNextStepTextViewInRegisterPageOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_one);

        //模拟下拉列表的数据
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("下拉列表项" + (i + 1));
        }

        mChooseSchoolCustomDropDownListView = (CustomDropDownListView)
                findViewById(R.id.choose_school_custom_drop_down_list_view);
        //这样写没用
//        mChooseSchoolCustomDropDownListView = new CustomDropDownListView(this);
        mChooseSchoolCustomDropDownListView.setItemsData("请选择你的学校", list);

        mSignInTextViewInRegisterPageOne = (TextView)
                findViewById(R.id.sign_in_text_view_in_register_page_one);
        mSignInTextViewInRegisterPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageOneActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mNextStepTextViewInRegisterPageOne = (TextView)
                findViewById(R.id.next_step_text_view_in_register_page_one);
        mNextStepTextViewInRegisterPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new
                        Intent(RegisterPageOneActivity.this,RegisterPageTwoActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

}
