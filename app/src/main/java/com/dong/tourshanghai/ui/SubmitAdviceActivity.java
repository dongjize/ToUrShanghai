package com.dong.tourshanghai.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dong.tourshanghai.R;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/7.
 */
public class SubmitAdviceActivity extends Activity {

    private EditText editText;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_submit_advice);

        editText = (EditText) findViewById(R.id.et_submit_advice);

        btnSubmit = (Button) findViewById(R.id.btn_submit_advice);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAdvice();
            }
        });
    }

    private void submitAdvice() {
        String str = editText.getText().toString();
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(SubmitAdviceActivity.this, "发送信息不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //VolleyUtils.
        }
    }
}
