package com.dong.tourshanghai.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dong.tourshanghai.R;

import java.util.List;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class GalleryViewPager extends ViewGroup {

    private List<View> views;
    private ViewPager mPager;
    private ViewPagerAdapter vAdapter;
    private LinearLayout pointGroup;

    private int[] pics = {R.mipmap.shanghai, R.mipmap.shanghai2, R.mipmap.shanghai3, R.mipmap.shanghai4};
    private String[] texts = {"aaaaaa", "bbbbbbbb", "cccccccc", "ddddddd"};

    private int lastPosition;
    private boolean isRunning = true;

    public GalleryViewPager(Context context) {
        super(context);
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }
    }

}
