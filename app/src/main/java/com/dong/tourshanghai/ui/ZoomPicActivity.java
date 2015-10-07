package com.dong.tourshanghai.ui;

import android.os.Bundle;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.view.ZoomImageView;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/10.
 */
public class ZoomPicActivity extends BaseActivity {

    private ZoomImageView zImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
    }
}
