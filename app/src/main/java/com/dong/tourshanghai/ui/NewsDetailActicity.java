package com.dong.tourshanghai.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.entity.NewsListEntity;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.utils.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Intro: 新闻详情页面
 * <p>
 * Programmer: dong
 * Date: 15/9/9.
 */
public class NewsDetailActicity extends NaviBaseActivity {

    private int newsId;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setNavigateMiddleTitle("资讯");
        setNavigateLeftButtonIsShow(true);
        setNavigateRightButtonIsShow(false);
    }

    @Override
    public void setContentView(LinearLayout contentView) {
        View childView = View.inflate(mContext, R.layout.layout_news_details, null);
        newsId = getIntent().getIntExtra("id", 0);
        contentView.addView(childView);
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

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * WebView展示新闻详情
     */
    @SuppressLint("JavascriptInterface")
    private void showDetailWebView() {
        if (webView != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    hideProgressDialog();
                }
            });
            webView.removeJavascriptInterface("searchBoxJavaBredge_");
            webView.canGoBack();
//            loadUrl();

        }

//        detailTemp = TemplateManager.getTemplateManager().getTemplate(mContext, TemplateManager.NEWS_DETAIL_TEMP);
//        detailTemp = detailTemp.replace(TemplateManager.NEWS_TITLE, newsDetail.news_title);
////        detailTemp = detailTemp.replace(TemplateManager.NEWS_CONTENT, newsDetail.news_content);
//
//        detailWebView.getSettings().setJavaScriptEnabled(true);
//
//        detailWebView.addJavascriptInterface(new Object() {
//            /**
//             * 全屏显示大图
//             * @param src
//             */
//            public void showBigPic(String src) {
//                Intent intent = new Intent(mContext, ZoomPicActivity.class);
//                intent.putExtra("picUrl", src.toString());
//                startActivity(intent);
//            }
//        }, "bigpic");
//        detailWebView.loadDataWithBaseURL("", detailTemp, "text/html", "UTF-8", null);

    }

    private void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
            showProgressDialog();
        }
    }

    @Override
    public void topRightButtonClick(View v) {

    }
}
