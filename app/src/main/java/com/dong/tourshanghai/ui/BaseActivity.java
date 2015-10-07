package com.dong.tourshanghai.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestListener;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.utils.AppManager;

/**
 * Intro: 所有Activity都继承的基类
 * <p>
 * Programmer: dong
 * Date: 15/9/5.
 */
public class BaseActivity extends FragmentActivity {

    //    protected MyTitlebar mTitlebar;
    CustomApplication mApplication;
    Toast mToast;
    public HttpManager httpManager;
    protected Context mContext;

    protected LinearLayout naviLayout, contentLayout;
    protected ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base);
        mContext = this;
        mApplication = CustomApplication.getInstance();

        naviLayout = (LinearLayout) findViewById(R.id.baselayout_navbar);
        contentLayout = (LinearLayout) findViewById(R.id.baselayout_content);
        setNaviBar(naviLayout);
        setContentView(contentLayout);

        AppManager.getInstance().addActivity(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请稍等..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
    }

    public void setNaviBar(LinearLayout navBar) {

    }

    public void setContentView(LinearLayout contentView) {

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

    /**
     * 显示加载框
     */
    protected void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    /**
     * 显示加载框
     *
     * @param msg 加载框文本
     */
    protected void showProgressDialog(String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    /**
     * 隐藏加载框
     */
    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss(); // 关闭进度框
            progressDialog.hide();
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
    public interface DataCallback<T> {
        public void onStart();

        public void onFailed();

        public void processData(T paramObject);

        public void onFinish();
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

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
