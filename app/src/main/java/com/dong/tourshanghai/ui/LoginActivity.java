package com.dong.tourshanghai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.entity.UserInfoEntity;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.utils.CommonUtils;
import com.dong.tourshanghai.utils.JSONParser;
import com.dong.tourshanghai.view.MyTitlebar;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Intro: 登录页面
 * <p>
 * Programmer: dong
 * Date: 15/9/4.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUserPhone, etPassword;
    private Button loginBtn;
    private TextView tvRegister, tvFindPwd, tvWarning;

    private String userPhone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        initTitlebarForLeft("登录", R.mipmap.btn_back, new MyTitlebar.OnHeaderButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {

            }
        });

        init();

    }

    private void init() {

        etUserPhone = (EditText) findViewById(R.id.et_userphone);
        etPassword = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvFindPwd = (TextView) findViewById(R.id.tv_forget_password);
        tvWarning = (TextView) findViewById(R.id.tv_login_warning);

        loginBtn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvFindPwd.setOnClickListener(this);
    }

    private void logIn() {
        userPhone = etUserPhone.getText().toString().trim();
        password = etUserPhone.getText().toString().trim();
        String md5pwd = CommonUtils.md5Util(password);

        HttpRequestVo vo = new HttpRequestVo();
        vo.requestUrl = ConstantValues.LOGIN_URL;
        vo.context = mContext;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userPhone", userPhone);
        map.put("password", md5pwd);
        vo.requestDataMap = map;
        JSONObject requestJson = new JSONObject();
        vo.parser = new JSONParser(UserInfoEntity.class);
        vo.requestJson = requestJson;

        //getDataFromServer(httpre);


        /**
         * 根据输入的用户名请求网络,查找对应的密码
         */
//        if (TextUtils.isEmpty(userId)) {
//            tvWarning.setText("输入信息不完整");
//        } else if (!password.equals(xxxxxx)) {
//            tvWarning.setText("输入密码有误,请重新输入");
//        } else {
//            //登录成功
//            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
//        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                logIn();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.animator.anim_activity_change, R.animator.anim_activity_exit);
                break;
            case R.id.tv_forget_password:
                showToast("找回密码...待实现...");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            overridePendingTransition(R.animator.anim_activity_exit, R.animator.anim_activity_back);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
