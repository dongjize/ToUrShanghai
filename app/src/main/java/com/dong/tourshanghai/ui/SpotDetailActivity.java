package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.view.MyTitlebar;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/12.
 */
public class SpotDetailActivity extends BaseActivity {

    private MyTitlebar.OnHeaderButtonClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fragment2);

        initTitlebarForBoth("", R.mipmap.btn_back, R.mipmap.ic_launcher, new MyTitlebar.OnHeaderButtonClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(SpotDetailActivity.this, "地图功能...待实现", Toast.LENGTH_LONG).show();
            }
        });
    }
}
