package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.fragment.Fragment0;
import com.dong.tourshanghai.fragment.Fragment1;
import com.dong.tourshanghai.fragment.Fragment2;
import com.dong.tourshanghai.fragment.Fragment3;
import com.dong.tourshanghai.utils.AppManager;

/**
 * Intro: 主界面容器
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mTab0, mTab1, mTab2, mTab3;

    private ImageView mImage0, mImage1, mImage2, mImage3;
    private TextView mText0, mText1, mText2, mText3;

    private Fragment mFragment0, mFragment1, mFragment2, mFragment3;

    private FragmentManager manager;

    private static long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(LinearLayout contentView) {
        View childView = View.inflate(this, R.layout.layout_main, null);
        initView(childView);
        contentView.addView(childView);
    }

    private void initView(View childView) {
        mTab0 = (LinearLayout) childView.findViewById(R.id.tab0);
        mTab1 = (LinearLayout) childView.findViewById(R.id.tab1);
        mTab2 = (LinearLayout) childView.findViewById(R.id.tab2);
        mTab3 = (LinearLayout) childView.findViewById(R.id.tab3);

        mImage0 = (ImageView) childView.findViewById(R.id.iv_tab0);
        mImage1 = (ImageView) childView.findViewById(R.id.iv_tab1);
        mImage2 = (ImageView) childView.findViewById(R.id.iv_tab2);
        mImage3 = (ImageView) childView.findViewById(R.id.iv_tab3);

        mText0 = (TextView) childView.findViewById(R.id.tv_tab0);
        mText1 = (TextView) childView.findViewById(R.id.tv_tab1);
        mText2 = (TextView) childView.findViewById(R.id.tv_tab2);
        mText3 = (TextView) childView.findViewById(R.id.tv_tab3);

        mTab0.setOnClickListener(MainActivity.this);
        mTab1.setOnClickListener(MainActivity.this);
        mTab2.setOnClickListener(MainActivity.this);
        mTab3.setOnClickListener(MainActivity.this);

        setSelect(0);
    }

    /**
     * 设置tab栏的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        resetImgs();
        switch (v.getId()) {
            case R.id.tab0:
                setSelect(0);
                break;
            case R.id.tab1:
                setSelect(1);
                break;
            case R.id.tab2:
                setSelect(2);
                break;
            case R.id.tab3:
                setSelect(3);
                break;

        }
    }

    /**
     * 切换tab动作
     *
     * @param i
     */
    private void setSelect(int i) {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (mFragment0 == null) {
                    mFragment0 = new Fragment0();
                    transaction.add(R.id.layout_middle_content, mFragment0);
                } else {
                    transaction.show(mFragment0);
                }
                mImage0.setImageResource(R.mipmap.tab_item_news_red);
                mText0.setTextColor(this.getResources().getColor(R.color.themecolor));
                break;
            case 1:
                if (mFragment1 == null) {
                    mFragment1 = new Fragment1();
                    transaction.add(R.id.layout_middle_content, mFragment1);
                } else {
                    transaction.show(mFragment1);
                }
                mImage1.setImageResource(R.mipmap.tab_item_map_red);
                mText1.setTextColor(this.getResources().getColor(R.color.themecolor));
                break;
            case 2:
                if (mFragment2 == null) {
                    mFragment2 = new Fragment2();
                    transaction.add(R.id.layout_middle_content, mFragment2);
                } else {
                    transaction.show(mFragment2);
                }
                mImage2.setImageResource(R.mipmap.tab_item_spots_red);
                mText2.setTextColor(this.getResources().getColor(R.color.themecolor));
                break;
            case 3:
                if (mFragment3 == null) {
                    mFragment3 = new Fragment3();
                    transaction.add(R.id.layout_middle_content, mFragment3);
                } else {
                    transaction.show(mFragment3);
                }
                mImage3.setImageResource(R.mipmap.tab_item_more_red);
                mText3.setTextColor(this.getResources().getColor(R.color.themecolor));
                break;
        }
        transaction.commit();
    }

    /**
     * tab按钮的点击效果
     */
    private void resetImgs() {
        mImage0.setImageResource(R.mipmap.tab_item_news);
        mImage1.setImageResource(R.mipmap.tab_item_map);
        mImage2.setImageResource(R.mipmap.tab_item_spots);
        mImage3.setImageResource(R.mipmap.tab_item_more);

        mText0.setTextColor(this.getResources().getColor(R.color.gray));
        mText1.setTextColor(this.getResources().getColor(R.color.gray));
        mText2.setTextColor(this.getResources().getColor(R.color.gray));
        mText3.setTextColor(this.getResources().getColor(R.color.gray));
    }

    /**
     * 隐藏所有的fragment,使其不可见
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mFragment0 != null) {
            transaction.hide(mFragment0);
        }
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }
        if (mFragment2 != null) {
            transaction.hide(mFragment2);
        }
        if (mFragment3 != null) {
            transaction.hide(mFragment3);
        }
    }

    /**
     * 连按两次返回键退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                AppManager.getInstance().appExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
