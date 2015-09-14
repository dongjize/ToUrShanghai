package com.dong.tourshanghai.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.view.MyTitlebar;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment2 extends BaseFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_fragment2, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initTitlebarForRight("景点", R.mipmap.composer_place, new MyTitlebar.OnHeaderButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {

            }
        });

    }


}
