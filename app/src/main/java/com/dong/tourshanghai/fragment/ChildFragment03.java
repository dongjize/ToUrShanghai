package com.dong.tourshanghai.fragment;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/7.
 */
public class ChildFragment03 extends BaseFragment0 {

    //判断是否第一次被加载
    private boolean isFirstLoad = true;

    @Override
    protected void lazyLoad() {

        if (isFirstLoad) {
            getNewsData(4);
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
