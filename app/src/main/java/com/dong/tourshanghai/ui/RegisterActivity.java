package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.utils.CommonUtils;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/9.
 */
public class RegisterActivity extends NaviBaseActivity implements View.OnClickListener {

    private Button btnRegister, btnSendCode;
    private EditText etUserPhone, etPassword, etIdCode;
    private String userPhone, password, idCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setNavigateMiddleTitle("注册");
        setNavigateLeftButtonIsShow(true);
        setNavigateRightButtonIsShow(false);

    }

    @Override
    public void setContentView(LinearLayout contentView) {
        View childView = View.inflate(mContext, R.layout.layout_register, null);

        btnRegister = (Button) findViewById(R.id.btn_register);
        btnSendCode = (Button) findViewById(R.id.btn_sendCode);
        etUserPhone = (EditText) findViewById(R.id.et_userphone);
        etPassword = (EditText) findViewById(R.id.et_password);
        etIdCode = (EditText) findViewById(R.id.et_idcode);
        btnRegister.setOnClickListener(this);
        btnSendCode.setOnClickListener(this);

        contentView.addView(childView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                sendRegister();
                break;
            case R.id.btn_sendCode:

                break;
        }
    }

    private void sendRegister() {
        userPhone = etUserPhone.getText().toString().trim();
        password = etUserPhone.getText().toString().trim();
        String md5Pwd = CommonUtils.md5Util(password);
        idCode = etIdCode.getText().toString().trim();

//        HttpRequestVo vo = new HttpRequestVo();
//        vo.requestUrl
    }

    private void sendCode() {
        Toast.makeText(RegisterActivity.this, "待实现", Toast.LENGTH_LONG).show();
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
