package com.dong.tourshanghai.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.entity.NewsListEntity;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.utils.JSONParser;
import com.dong.tourshanghai.utils.TemplateManager;
import com.dong.tourshanghai.view.MyTitlebar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Intro: 新闻详情页面
 * <p>
 * Programmer: dong
 * Date: 15/9/9.
 */
public class NewsDetailActicity extends BaseActivity {

    private int newsId;
    private NewsListEntity.NewsModel newsModel;
    private WebView detailWebView;
    private String detailTemp;

    private int minFontSize = 12, maxFontSize = 20;
    private int getMinFontSize = 16;
    private int lineHeight = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
        initTitlebarForBoth("资讯", R.mipmap.btn_back, R.mipmap.ic_launcher,
                new MyTitlebar.OnHeaderButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                        finish();
                    }

                    @Override
                    public void onRightClick() {
                        Toast.makeText(NewsDetailActicity.this, "评论功能...待实现...", Toast.LENGTH_SHORT).show();
                    }
                });

        newsId = getIntent().getIntExtra("id", 0);
        getNewsDetailData(newsId);
    }


    private void getNewsDetailData(int id) {
        HttpRequestVo vo = new HttpRequestVo();
        vo.requestUrl = ConstantValues.GET_NEWS_DETAIL;
        vo.context = mContext;
        JSONObject req = new JSONObject();
        try {
            req.put("requestparams", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vo.parser = new JSONParser(NewsListEntity.NewsModel.class);
        vo.requestJson = req;

        getDataFromServer(HttpManager.GET_METHOD, vo, new DataCallback<HashMap<String, Object>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFailed() {

            }

            @Override
            public void processData(HashMap<String, Object> paramObject) {
                newsModel = (NewsListEntity.NewsModel) paramObject.get("result");
                showDetailWebView(newsModel, 17, 27);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * WebView展示新闻详情
     *
     * @param newsDetail
     * @param footSize
     * @param lineHeight
     */
    @SuppressLint("JavascriptInterface")
    protected void showDetailWebView(NewsListEntity.NewsModel newsDetail, int footSize, int lineHeight) {
        detailTemp = TemplateManager.getTemplateManager().getTemplate(mContext, TemplateManager.NEWS_DETAIL_TEMP);
        detailTemp = detailTemp.replace(TemplateManager.NEWS_TITLE, newsDetail.news_title);
        detailTemp = detailTemp.replace(TemplateManager.NEWS_CONTENT, newsDetail.news_content);

        detailWebView.getSettings().setJavaScriptEnabled(true);

        detailWebView.addJavascriptInterface(new Object() {
            /**
             * 全屏显示大图
             * @param src
             */
            public void showBigPic(String src) {
                Intent intent = new Intent(mContext, ZoomPicActivity.class);
                intent.putExtra("picUrl", src.toString());
                startActivity(intent);
            }
        }, "bigpic");
        detailWebView.loadDataWithBaseURL("", detailTemp, "text/html", "UTF-8", null);

    }
}
