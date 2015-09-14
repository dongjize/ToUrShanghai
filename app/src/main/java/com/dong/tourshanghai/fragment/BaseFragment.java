package com.dong.tourshanghai.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestListener;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.view.MyTitlebar;

/**
 * Intro: Fragment基类
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public abstract class BaseFragment extends Fragment {

    public View view;
    public HttpManager httpManager;

    public MyTitlebar mTitleBar;
    Toast mToast;
    protected Context mContext;

    private Handler handler;

    public void runOnWorkThread(Runnable action) {
        new Thread(action).start();
    }

    public void runOnUiThread(Runnable action) {
        handler.post(action);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 初始化界面
     *
     * @param inflater
     * @return
     */
    public abstract View initView(LayoutInflater inflater);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    public abstract void initData(Bundle savedInstanceState);


    /**
     * 标题栏只有title
     *
     * @param titleName
     */
    public void initTitlebarForOnlyTitle(String titleName) {
        mTitleBar = (MyTitlebar) view.findViewById(R.id.common_titlebar);
        mTitleBar.initStyle(MyTitlebar.HeaderStyle.DEFAULT_TITLE);
        mTitleBar.setDefaultTitle(titleName);
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
        mTitleBar = (MyTitlebar) view.findViewById(R.id.common_titlebar);
        mTitleBar.initStyle(MyTitlebar.HeaderStyle.TITLE_DOUBLE_BUTTON);
        mTitleBar.setTitleAndLeftButton(titleName, leftDrawableId, listener);
        mTitleBar.setTitleAndRightButton(titleName, rightDrawableId, listener);
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
        mTitleBar = (MyTitlebar) view.findViewById(R.id.common_titlebar);
        mTitleBar.initStyle(MyTitlebar.HeaderStyle.TITLE_LEFT_BUTTON);
        mTitleBar.setTitleAndLeftButton(titleName, leftDrawableId, listener);
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
        mTitleBar = (MyTitlebar) view.findViewById(R.id.common_titlebar);
        mTitleBar.initStyle(MyTitlebar.HeaderStyle.TITLE_RIGHT_BUTTON);
        mTitleBar.setTitleAndRightButton(titleName, rightDrawableId, listener);
    }


    public void showToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
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
     * 回调接口
     *
     * @param <T> 数据类型,一般为HashMap
     */
    public abstract interface DataCallback<T> {
        public abstract void onStart();

        public abstract void onFailed();

        /**
         * 处理服务器返回的数据
         *
         * @param paramObject 服务器返回的数据实体
         */
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
        final BaseHandler handler = new BaseHandler(mContext, callback);
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
            /**
             *
             */
            httpManager.sendGetRequest();
        } else if (requestType == HttpManager.POST_METHOD) {
            /**
             *
             */
            httpManager.sendPostRequest();
        }
    }


    /**
     * 当Fragment为stop状态时,http请求队列取消所有请求
     */
    @Override
    public void onStop() {
        super.onStop();
        CustomApplication.getHttpQueue().cancelAll("reqGet");
        CustomApplication.getHttpQueue().cancelAll("reqPost");
    }
}
