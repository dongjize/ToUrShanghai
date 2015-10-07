package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dong.tourshanghai.R;

/**
 * Intro:
 * Programmer: dong
 * Date: 15/10/6.
 */
public class NaviBaseActivity extends BaseActivity {

    protected RelativeLayout leftLayout;
    protected ImageView rightButton;
    protected LinearLayout middleLayout;
    protected TextView middleTitleView;
    protected TextView rightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setNaviBar(LinearLayout naviBar) {
        View naviBarView = View.inflate(this, R.layout.common_navbar, null);
        naviBar.addView(naviBarView);

        leftLayout = (RelativeLayout) naviBarView.findViewById(R.id.layoutNavLeft);
        rightButton = (ImageView) naviBarView.findViewById(R.id.btnNavRight);
        middleLayout = (LinearLayout) naviBarView.findViewById(R.id.layout_nav_middle);
        middleTitleView = (TextView) naviBarView.findViewById(R.id.nav_middletitle);
        rightTextView = (TextView) naviBarView.findViewById(R.id.tv_nav_right);

        leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topLeftButtonClick(v);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topRightButtonClick(v);
            }
        });
        rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topRightTextClick(v);
            }
        });
    }

    public void setNavigateLeftButtonIsShow(boolean visible) {
        if (visible) {
            leftLayout.setVisibility(View.VISIBLE);
        } else {
            leftLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void setNavigateRightButtonIsShow(boolean visible) {
        if (visible) {
            rightButton.setVisibility(View.VISIBLE);
        } else {
            rightButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setNavigateMiddleTitle(String title) {
        middleTitleView.setText(title);
    }

    public void setNavigateMiddleTitleView(View centerView) {
        middleLayout.removeView(middleTitleView);
        middleLayout.addView(centerView);
    }

    public void setNavigateRightImageViewImage(int img) {
        ViewGroup.LayoutParams params = rightButton.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        rightButton.setLayoutParams(params);
        rightButton.setImageResource(img);
    }

    public void setNavigateRightTextViewText(String text) {
        rightTextView.setText(text);
    }

    public void topLeftButtonClick(View v) {
        if (leftLayout.getVisibility() == View.VISIBLE) {
            this.finish();
        }
    }

    public void topRightButtonClick(View v) {

    }

    public void topRightTextClick(View v) {

    }
}
