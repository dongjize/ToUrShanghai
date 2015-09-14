package com.dong.tourshanghai.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.dong.tourshanghai.adapter.NewsItemAdapter;
import com.dong.tourshanghai.entity.NewsListEntity;

import java.util.List;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/7.
 */
public class ChildFragment00 extends BaseFragment0 {

    private List<NewsListEntity> mList;
    private NewsItemAdapter mAdapter;
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

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

//        getNewsData(1);

        //mListView.setXListViewListener(this);
//        mHandler = new Handler();


    }

    @Override
    protected void lazyLoad() {

        if (isFirstLoad) {
            getNewsData(1);
            isFirstLoad = false;
        }
    }


    @Override
    public void onRefresh() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getNewsData(1);
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
    }

    @Override
    public void onLoadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
    }


}
