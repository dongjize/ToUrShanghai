package com.dong.tourshanghai.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/7.
 */
public class ChildFragment05 extends BaseFragment0 {

    //判断是否第一次被加载
    private boolean isFirstLoad = true;

    @Override
    public View initView(LayoutInflater inflater) {
        return super.initView(inflater);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        /**
         * 这里要根据ChildFragment对应的新闻type,加载相应类型的新闻数据
         */
        //mListView.setPullLoadEnable(true);

        //getNewsData(1);

        //mListView.setXListViewListener(this);
//        mHandler = new Handler();


    }

    @Override
    protected void lazyLoad() {

        if (isFirstLoad) {
            getNewsData(6);
            isFirstLoad = false;
        }

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
