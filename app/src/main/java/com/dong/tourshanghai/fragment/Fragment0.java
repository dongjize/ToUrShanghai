package com.dong.tourshanghai.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dong.tourshanghai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Intro: 新闻模块的主Fragment,其下包含了多个子fragment
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment0 extends BaseFragment implements OnClickListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private Fragment fragment0, fragment1, fragment2, fragment3, fragment4, fragment5, fragment6;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    //顶部标题标签
    private TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6;
    private List<TextView> tvs = new ArrayList<TextView>();


    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_fragment0, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initTitlebarForOnlyTitle("资讯");
        mViewPager = (ViewPager) getActivity().findViewById(R.id.news_viewpager);
        loadView();

        FragmentManager manager = getChildFragmentManager();

        mAdapter = new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(7);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTabTextColor();

                switch (position) {
                    case 0:
                        tv0.setTextColor(getResources().getColor(R.color.themecolor));
                        tv0.setTextSize(16);
                        break;
                    case 1:
                        tv1.setTextColor(getResources().getColor(R.color.themecolor));
                        tv1.setTextSize(16);
                        break;
                    case 2:
                        tv2.setTextColor(getResources().getColor(R.color.themecolor));
                        tv2.setTextSize(16);
                        break;
                    case 3:
                        tv3.setTextColor(getResources().getColor(R.color.themecolor));
                        tv3.setTextSize(16);
                        break;
                    case 4:
                        tv4.setTextColor(getResources().getColor(R.color.themecolor));
                        tv4.setTextSize(16);
                        break;
                    case 5:
                        tv5.setTextColor(getResources().getColor(R.color.themecolor));
                        tv5.setTextSize(16);
                        break;
                    case 6:
                        tv6.setTextColor(getResources().getColor(R.color.themecolor));
                        tv6.setTextSize(16);
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadView() {
        tv0 = (TextView) getActivity().findViewById(R.id.tv_news_type0);
        tv1 = (TextView) getActivity().findViewById(R.id.tv_news_type1);
        tv2 = (TextView) getActivity().findViewById(R.id.tv_news_type2);
        tv3 = (TextView) getActivity().findViewById(R.id.tv_news_type3);
        tv4 = (TextView) getActivity().findViewById(R.id.tv_news_type4);
        tv5 = (TextView) getActivity().findViewById(R.id.tv_news_type5);
        tv6 = (TextView) getActivity().findViewById(R.id.tv_news_type6);

        fragment0 = new ChildFragment00();
        fragment1 = new ChildFragment01();
        fragment2 = new ChildFragment02();
        fragment3 = new ChildFragment03();
        fragment4 = new ChildFragment04();
        fragment5 = new ChildFragment05();
        fragment6 = new ChildFragment06();

        mFragments.add(fragment0);
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
        mFragments.add(fragment5);
        mFragments.add(fragment6);

        tvs.add(tv0);
        tvs.add(tv1);
        tvs.add(tv2);
        tvs.add(tv3);
        tvs.add(tv4);
        tvs.add(tv5);
        tvs.add(tv6);

        tv0.setTextColor(getResources().getColor(R.color.themecolor));
        tv0.setTextSize(16);
    }

    private void resetTabTextColor() {
        tv0.setTextColor(getResources().getColor(R.color.black));
        tv1.setTextColor(getResources().getColor(R.color.black));
        tv2.setTextColor(getResources().getColor(R.color.black));
        tv3.setTextColor(getResources().getColor(R.color.black));
        tv4.setTextColor(getResources().getColor(R.color.black));
        tv5.setTextColor(getResources().getColor(R.color.black));
        tv6.setTextColor(getResources().getColor(R.color.black));

        tv0.setTextSize(14);
        tv1.setTextSize(14);
        tv2.setTextSize(14);
        tv3.setTextSize(14);
        tv4.setTextSize(14);
        tv5.setTextSize(14);
        tv6.setTextSize(14);
    }

    @Override
    public void onClick(View v) {


    }
}
