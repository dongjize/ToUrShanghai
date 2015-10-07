package com.dong.tourshanghai.fragment;

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
public class NaviBaseFragment extends BaseFragment {
    protected RelativeLayout leftLayout;
    protected ImageView rightButton;
    protected LinearLayout middleLayout;
    protected TextView middleTitleView;

    @Override
    public void setNaviBar(LinearLayout naviBar) {
        View naviBarView = View.inflate(mContext, R.layout.common_navbar, null);
        naviBar.addView(naviBarView);

        leftLayout = (RelativeLayout) naviBarView.findViewById(R.id.layoutNavLeft);
        rightButton = (ImageView) naviBarView.findViewById(R.id.btnNavRight);
        middleLayout = (LinearLayout) naviBarView.findViewById(R.id.layout_nav_middle);
        middleTitleView = (TextView) naviBarView.findViewById(R.id.nav_middletitle);

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

    public void setNavigateRightImageViewImage(int img) {
        ViewGroup.LayoutParams layoutParams = rightButton.getLayoutParams();
        rightButton.setLayoutParams(layoutParams);
        rightButton.setImageResource(img);
    }

    public void setNavigateMiddleTitle(String title) {
        middleTitleView.setText(title);
    }

    public void setNavigateMiddleTitleView(View centerView) {
        middleLayout.removeView(middleTitleView);
        middleLayout.addView(centerView);
    }

    public void topLeftButtonClick(View v) {
        if (leftLayout.getVisibility() == View.VISIBLE) {
            getActivity().finish();
        }
    }

    public void topRightButtonClick(View v) {

    }

}
