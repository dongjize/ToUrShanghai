package com.dong.tourshanghai.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestListener;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.view.MyTitlebar;

/**
 * Intro: 所有Activity都继承的基类
 * <p>
 * Programmer: dong
 * Date: 15/9/5.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected MyTitlebar mTitlebar;
    CustomApplication mApplication;
    Toast mToast;
    public HttpManager httpManager;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = CustomApplication.getInstance();

    }

    /**
     * 标题栏只有title
     *
     * @param titleName
     */
    public void initTitlebarForOnlyTitle(String titleName) {
        mTitlebar = (MyTitlebar) findViewById(R.id.common_titlebar);
        mTitlebar.initStyle(MyTitlebar.HeaderStyle.DEFAULT_TITLE);
        mTitlebar.setDefaultTitle(titleName);
    }

    /**
     * 初始化带左右按钮的标题栏
     *
     * @param titleName
     * @param leftDrawableId
     * @param rightDrawableId
     * @param listener
     */
    public void initTitlebarForBoth(String titleName, int leftDrawableId, int rightDrawableId,
                                    MyTitlebar.OnHeaderButtonClickListener listener) {
        mTitlebar = (MyTitlebar) findViewById(R.id.common_titlebar);
        mTitlebar.initStyle(MyTitlebar.HeaderStyle.TITLE_DOUBLE_BUTTON);
        mTitlebar.setTitleAndLeftButton(titleName, leftDrawableId, listener);
        mTitlebar.setTitleAndRightButton(titleName, rightDrawableId, listener);
    }

    /**
     * 初始化带左按钮的标题栏
     *
     * @param titleName
     * @param leftDrawableId
     * @param listener
     */
    public void initTitlebarForLeft(String titleName, int leftDrawableId,
                                    MyTitlebar.OnHeaderButtonClickListener listener) {
        mTitlebar = (MyTitlebar) findViewById(R.id.common_titlebar);
        mTitlebar.initStyle(MyTitlebar.HeaderStyle.TITLE_DOUBLE_BUTTON);
        mTitlebar.setTitleAndLeftButton(titleName, leftDrawableId, listener);
    }

    /**
     * 初始化带右按钮的标题栏
     *
     * @param titleName
     * @param rightDrawableId
     * @param listener
     */
    public void initTitlebarForRight(String titleName, int rightDrawableId,
                                     MyTitlebar.OnHeaderButtonClickListener listener) {
        mTitlebar = (MyTitlebar) findViewById(R.id.common_titlebar);
        mTitlebar.initStyle(MyTitlebar.HeaderStyle.TITLE_RIGHT_BUTTON);
        mTitlebar.setTitleAndRightButton(titleName, rightDrawableId, listener);
    }

    public void showToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });
        }
    }

    class BaseHandler extends Handler {
        private Context context;
        private DataCallback callback;

        private BaseHandler(Context context, DataCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HttpRequestListener.EVENT_GET_DATA_SUCCESS) {
                if (msg.obj == null) {
                    callback.onFailed();
                } else {
                    callback.processData(msg.obj);
                }
            } else {
                callback.onFailed();
            }
            callback.onFinish();
        }
    }

    /**
     * 回调接口,定义了处理回调结果的四个阶段
     *
     * @param <T> 返回的数据类型
     */
    public abstract interface DataCallback<T> {
        public abstract void onStart();

        public abstract void onFailed();

        public abstract void processData(T paramObject);

        public abstract void onFinish();
    }

    /**
     * 从服务器上获取数据,并回调处理
     *
     * @param requestType
     * @param requestVo
     */
    protected void getDataFromServer(int requestType, HttpRequestVo requestVo, DataCallback callback) {
        final BaseHandler handler = new BaseHandler(this, callback);
        httpManager = new HttpManager(mContext, new HttpRequestListener() {
            @Override
            public void action(int actionCode, Object object) {
                Message msg = Message.obtain();
                switch (actionCode) {
                    case HttpRequestListener.EVENT_NO_NETWORK:
                        msg.what = HttpRequestListener.EVENT_NO_NETWORK;
                        break;
                    case HttpRequestListener.EVENT_NETWORK_ERROR:
                        msg.what = HttpRequestListener.EVENT_NETWORK_ERROR;
                        break;
                    case HttpRequestListener.EVENT_GET_DATA_ERROR:
                        msg.what = HttpRequestListener.EVENT_GET_DATA_ERROR;
                        msg.obj = null;
                        break;
                    case HttpRequestListener.EVENT_GET_DATA_SUCCESS:
                        msg.obj = object;
                        msg.what = HttpRequestListener.EVENT_GET_DATA_SUCCESS;
                        break;
                    default:
                        break;
                }
                handler.sendMessage(msg);
            }
        }, requestVo);
        callback.onStart();
        if (requestType == HttpManager.GET_METHOD) {
            httpManager.sendGetRequest();
        } else if (requestType == HttpManager.POST_METHOD) {
            httpManager.sendPostRequest();
        }
    }

    /**
     * 当Fragment为stop状态时,http请求队列取消所有请求
     */
    @Override
    protected void onStop() {
        super.onStop();
        CustomApplication.getHttpQueue().cancelAll("reqGet");
        CustomApplication.getHttpQueue().cancelAll("reqPost");
    }

}
