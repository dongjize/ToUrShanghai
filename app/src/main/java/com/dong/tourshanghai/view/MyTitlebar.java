package com.dong.tourshanghai.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dong.tourshanghai.R;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/4.
 */
public class MyTitlebar extends RelativeLayout {
    private View mTitlebar;
    private LayoutInflater mInflater;

    private LinearLayout leftContainer, rightContainer, middleContainer;
    private ImageButton mLeftImgButton, mRightImgButton;

    private TextView tvTitle;

    //导航栏样式枚举
    public enum HeaderStyle {
        DEFAULT_TITLE, TITLE_LEFT_BUTTON, TITLE_RIGHT_BUTTON, TITLE_DOUBLE_BUTTON;
    }

    private OnHeaderButtonClickListener headerButtonClickListener;

    //导航栏点击回调接口
    public interface OnHeaderButtonClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public MyTitlebar(Context context) {
        super(context);
        init(context);
    }

    public MyTitlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mTitlebar = mInflater.inflate(R.layout.layout_titlebar, null);
        addView(mTitlebar);
        initViews();
    }

    private void initViews() {
        leftContainer = (LinearLayout) mTitlebar.findViewById(R.id.titlebar_container_left);
        rightContainer = (LinearLayout) mTitlebar.findViewById(R.id.titlebar_container_right);
        middleContainer = (LinearLayout) mTitlebar.findViewById(R.id.titlebar_container_middle);

        tvTitle = (TextView) mTitlebar.findViewById(R.id.tv_title_middle);
    }

    /**
     * 设定导航栏的样式
     *
     * @param style
     */
    public void initStyle(HeaderStyle style) {
        switch (style) {
            case DEFAULT_TITLE:
                defaultTitle();
                break;
            case TITLE_LEFT_BUTTON:
                defaultTitle();
                titleLeft();
                break;
            case TITLE_RIGHT_BUTTON:
                defaultTitle();
                titleRight();
                break;
            case TITLE_DOUBLE_BUTTON:
                titleLeft();
                titleRight();
                break;
        }
    }

    /**
     * 默认样式--文字标题
     */
    private void defaultTitle() {
        leftContainer.removeAllViews();
        rightContainer.removeAllViews();
    }

    /**
     * 左侧自定义控件
     */
    private void titleLeft() {
        View leftView = mInflater.inflate(R.layout.common_titlebar_btn, null);
        leftContainer.addView(leftView);

        mLeftImgButton = (ImageButton) leftView.findViewById(R.id.ib_header);
        mLeftImgButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headerButtonClickListener != null) {
                    headerButtonClickListener.onLeftClick();
                }
            }
        });

    }

    /**
     * 右侧自定义控件
     */
    private void titleRight() {
        View rightView = mInflater.inflate(R.layout.common_titlebar_btn, null);
        rightContainer.addView(rightView);
        mRightImgButton = (ImageButton) rightView.findViewById(R.id.ib_header);
        mRightImgButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headerButtonClickListener != null) {
                    headerButtonClickListener.onRightClick();
                }
            }
        });
    }

    /**
     * 设置默认标题
     *
     * @param title
     */
    public void setDefaultTitle(String title) {
        if (title != null) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边按钮
     *
     * @param title
     * @param bgId
     * @param headerListener
     */
    public void setTitleAndRightButton(String title, int bgId,
                                       OnHeaderButtonClickListener headerListener) {
        setDefaultTitle(title);
        rightContainer.setVisibility(View.VISIBLE);
        if (mRightImgButton != null && bgId > 0) {
            mRightImgButton.setBackgroundResource(bgId);
            setOnHeaderButtonListener(headerButtonClickListener);

        }
    }

    /**
     * 设置左边按钮
     *
     * @param title
     * @param bgId
     * @param headerListener
     */
    public void setTitleAndLeftButton(String title, int bgId,
                                      OnHeaderButtonClickListener headerListener) {
        setDefaultTitle(title);
        if (mLeftImgButton != null && bgId > 0) {
            mLeftImgButton.setBackgroundResource(bgId);
            setOnHeaderButtonListener(headerButtonClickListener);
        }

    }

    /**
     * 对外暴露的点击方法
     *
     * @param listener
     */
    public void setOnHeaderButtonListener(OnHeaderButtonClickListener listener) {
        this.headerButtonClickListener = listener;

    }
}
