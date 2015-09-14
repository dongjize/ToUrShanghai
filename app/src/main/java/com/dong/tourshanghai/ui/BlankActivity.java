package com.dong.tourshanghai.ui;

import android.os.Bundle;

/**
 * Intro: 空白Activity,根据传递过来的布局文件ID加载页面,用于仅仅显示
 * <p>
 * Programmer: dong
 * Date: 15/9/12.
 */
public class BlankActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutID = getIntent().getExtras().getInt("layoutID");
        setContentView(layoutID);
    }
}
