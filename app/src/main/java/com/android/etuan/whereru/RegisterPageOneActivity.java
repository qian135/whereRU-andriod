package com.android.etuan.whereru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.etuan.whereru.utils.DpAndPxSwitch;
import com.android.etuan.whereru.utils.InterfaceConstant;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class RegisterPageOneActivity extends Activity {

    /* 校园名下拉列表相关代码 */

    private TextView mDropdownTextView;
    private ImageView mDropdownImageView;

    private PopupWindow mPopupWindow;
    private ListView mCustomSpinnerListView;
    private List<String> mCustomSpinnerListViewContent;

    private TextView mSchoolNameTextView;//被选中的学校


    //在注册界面1的登陆TextView，点击返回登陆界面
    private TextView mSignInTextViewInRegisterPageOne;

    //在注册界面1的下一步TextView，点击跳转到注册界面2
    private TextView mNextStepTextViewInRegisterPageOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_one);

        initCustomSpinner();

        //请求学校名称数据
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                InterfaceConstant.SCHOOL_NAME_LIST_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                mCustomSpinnerListViewContent.add(jsonArray.getJSONObject(i).getString("name"));
                            } catch (JSONException e) {
                                System.out.println("JSON解析出错 SchoolList");
                            }

                            mCustomSpinnerListView.setAdapter(
                                    new CustomSpinnerAdapter(RegisterPageOneActivity.this,
                                            mCustomSpinnerListViewContent));

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        MyVolleySingleton.getInstance(this.getApplicationContext())
                .addToRequestQueue(jsonArrayRequest);

        //跳转到登陆界面
        mSignInTextViewInRegisterPageOne = (TextView)
                findViewById(R.id.sign_in_text_view_in_register_page_one);
        mSignInTextViewInRegisterPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPageOneActivity.this, LoginInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSchoolNameTextView = (TextView) findViewById(R.id.school_name_dropdown_text_view);
        //跳转到注册界面2
        mNextStepTextViewInRegisterPageOne = (TextView)
                findViewById(R.id.next_step_text_view_in_register_page_one);
        mNextStepTextViewInRegisterPageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSchoolNameTextView.getText().toString().equals("请选择你的学校")) {
                    Intent intent = new
                            Intent(RegisterPageOneActivity.this, RegisterPageTwoActivity.class);
                    intent.putExtra("schoolName", mSchoolNameTextView.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterPageOneActivity.this,
                            "请选择你的学校", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initCustomSpinner() {

        mDropdownTextView = (TextView) findViewById(R.id.school_name_dropdown_text_view);
        mDropdownImageView = (ImageView) findViewById(R.id.school_name_dropdown_image_view);

        mCustomSpinnerListView = (ListView) getLayoutInflater()
                .inflate(R.layout.custom_school_name_spinner_list_view, null)
                .findViewById(R.id.custom_school_name_spinner_list_view);

        mCustomSpinnerListViewContent = new ArrayList<>();

        //减60是因为左右两边各留了30dp
        int popupWindowWidth = getResources().getDisplayMetrics().widthPixels
                - DpAndPxSwitch.dp2px(this, 60)
                - mDropdownImageView.getLayoutParams().width;

        mPopupWindow = new PopupWindow(mCustomSpinnerListView, popupWindowWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.transparent));

        mDropdownTextView = (TextView) findViewById(R.id.school_name_dropdown_text_view);
        mDropdownImageView = (ImageView) findViewById(R.id.school_name_dropdown_image_view);

        mDropdownImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(mDropdownTextView);
            }
        });

        mCustomSpinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDropdownTextView.setText(mCustomSpinnerListView
                        .getItemAtPosition(position).toString());//选哪项填哪项
                mPopupWindow.dismiss();
            }
        });

    }

    class CustomSpinnerAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mList;

        private LayoutInflater mLayoutInflater;

        public CustomSpinnerAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(
                        R.layout.custom_school_name_spinner_list_view_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTextView = (TextView) convertView.findViewById(
                        R.id.custom_school_name_spinner_list_view_item);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mTextView.setText(mList.get(position));
            return convertView;
        }
    }

    public class ViewHolder {
        public TextView mTextView;
    }

}
