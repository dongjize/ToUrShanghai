package com.dong.tourshanghai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.adapter.NewsItemAdapter;
import com.dong.tourshanghai.entity.NewsListEntity;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.ui.NewsDetailActicity;
import com.dong.tourshanghai.utils.BitmapCache;
import com.dong.tourshanghai.utils.CommonUtils;
import com.dong.tourshanghai.utils.JSONParser;
import com.dong.tourshanghai.view.XListView;
import com.dong.tourshanghai.view.XListView.IXListViewListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Intro: 新闻列表父类,ChildFragment0x均继承该类
 * <p/>
 * Programmer: dong
 * Date: 15/9/8.
 */
public class BaseFragment0 extends BaseFragment implements IXListViewListener {
    // 图片轮播者
    protected ViewPager viewPager;
    // 轮播图列表
    protected List<View> views;
    // 置顶新闻列表
    protected ArrayList<NewsListEntity.NewsModel> mImgList;
    // 轮播图适配器
    protected ViewPagerAdapter vAdapter;

    protected boolean isRunning = true;
    // 圆点线性布局
    protected LinearLayout pointGroup;

    protected int lastPosition;

    private static final int POST_DELAY = 5000;
    // 新闻列表视图
    protected XListView mListView;
    // 新闻列表
    protected ArrayList<NewsListEntity.NewsModel> mList;
    // 新闻item适配器
    protected NewsItemAdapter mAdapter;

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {

    }

    protected void onInvisible() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("POSITION", position + "");
                Intent detailIntent = new Intent(mContext, NewsDetailActicity.class);
                detailIntent.putExtra("id", mList.get(position).news_id);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void setContentView(LinearLayout contentLayout) {
        View childView = View.inflate(mContext, R.layout.fragment0_child, null);

        viewPager = (ViewPager) childView.findViewById(R.id.gallery_viewpager);
        mImgList = new ArrayList<NewsListEntity.NewsModel>();
        pointGroup = (LinearLayout) childView.findViewById(R.id.point_group);
        views = new ArrayList<View>();
//        mListView = (XListView) view.findViewById(R.id.xlv_fragment0);
        mListView = (XListView) childView.findViewById(R.id.lv_fragment0);
        mList = new ArrayList<NewsListEntity.NewsModel>();

        contentLayout.addView(childView);
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (viewPager.getCurrentItem() == views.size() - 1) {
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, POST_DELAY);
            }
        }
    };

    /**
     * 向服务端发送请求参数,获得json数据
     *
     * @param type 1:热点 2:闲情 3:专题 4:推荐 5:-- 6:--
     */
    protected void getNewsData(int type) {
        HttpRequestVo vo = new HttpRequestVo();
        vo.requestUrl = ConstantValues.GET_NEWS_LIST;
        vo.context = mContext;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("istop", "0");
        map.put("type", type + "");
        vo.requestDataMap = map;
        JSONObject requestJson = new JSONObject();  //目前貌似没用
        vo.parser = new JSONParser(NewsListEntity.class);
        vo.requestJson = requestJson;

        getDataFromServer(HttpManager.GET_METHOD, vo, new DataCallback<HashMap<String, Object>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFailed() {

            }

            /**
             * 处理返回的news列表数据
             * @param paramObject
             */
            @Override
            public void processData(HashMap<String, Object> paramObject) {
                NewsListEntity newsEntity = (NewsListEntity) paramObject.get("datamap");
                ArrayList<NewsListEntity.NewsModel> newsList = newsEntity.newsList;
                mList.addAll(newsList);
                if (mListView.getAdapter() == null) {
                    mAdapter = new NewsItemAdapter(getContext(), mList);
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }

                for (int i = 0; i < newsList.size(); i++) {
                    NewsListEntity.NewsModel item = newsList.get(i);
                    if (item.istop == 1) {
                        mImgList.add(item);
                    }
                }

                for (int i = 0; i < mImgList.size(); i++) {
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    final NetworkImageView imageView = new NetworkImageView(mContext);
                    String imgUrl = ConstantValues.PIC_URL_NEWS + mImgList.get(i).news_imgurl;
                    ImageLoader loader = new ImageLoader(CustomApplication.getHttpQueue(), new BitmapCache());
                    imageView.setDefaultImageResId(R.mipmap.shanghai);
                    imageView.setErrorImageResId(R.mipmap.shanghai);
                    imageView.setImageUrl(imgUrl, loader);

                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    views.add(imageView);

                    ImageView point = new ImageView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = 10;
                    params.rightMargin = 10;
                    point.setLayoutParams(params);
                    point.setBackgroundResource(R.drawable.gallery_viewpager_point);
                    if (i == 0) {
                        point.setEnabled(true);
                    } else {
                        point.setEnabled(false);
                    }
                    pointGroup.addView(point);

                }

                if (mImgList.size() == 0) {
                    viewPager.setVisibility(View.GONE);
                    pointGroup.setVisibility(View.GONE);
                } else {
                    vAdapter = new ViewPagerAdapter(views);
                    viewPager.setAdapter(vAdapter);

                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (position == 0) {
                                lastPosition = views.size() - 1;
                            } else {
                                lastPosition = position - 1;
                            }
                            pointGroup.getChildAt(position).setEnabled(true);
                            pointGroup.getChildAt(lastPosition).setEnabled(false);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                        }

                    });

                    handler.sendEmptyMessageDelayed(0, POST_DELAY);
                }


            }

            @Override
            public void onFinish() {
            }

        });

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 上方轮播图对应的Adapter
     */
    class ViewPagerAdapter extends PagerAdapter {

        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ((ViewPager) container).addView(views.get(position));
            views.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(getActivity(), NewsDetailActicity.class);
                    detailIntent.putExtra("id", mImgList.get(position).news_id);
                    startActivity(detailIntent);
                }
            });
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }
    }

    /**
     * XListView加载
     */
    protected void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(CommonUtils.getBriefCurrentTime());
    }
}
