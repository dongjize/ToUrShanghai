package com.dong.tourshanghai.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;


/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class SplashActivity extends BaseActivity {


    private static final int SHOW_UPDATE_DIALOG = 0;
    private static final int ENTER_HOME = 1;
    private static final int URL_ERROR = 2;
    private static final int NETWORK_ERROR = 3;
    private static final int CONNECT_TIMEOUT = 5 * 1000;

    public String apkVersion, apkUrl, description, appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUpdate();
            }
        }, 2000);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case URL_ERROR:
                    enterHome();
                    break;
                case NETWORK_ERROR:
                    enterHome();
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 检查是否有新版本可以更新
     */
    private void checkUpdate() {
        HttpRequestVo vo = new HttpRequestVo();
        vo.requestUrl = ConstantValues.CHECK_UPDATE_URL;
        vo.context = mContext;
        HashMap<String, String> map = new HashMap<String, String>();
        vo.requestDataMap = map;
        JSONObject requestJson = new JSONObject();  //目前貌似没用
        vo.requestJson = requestJson;
        vo.parser = null;

        getDataFromServer(HttpManager.GET_METHOD, vo, new DataCallback<JSONObject>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFailed() {
                showToast("获取网络数据失败");
                enterHome();
            }

            @Override
            public void processData(JSONObject jsonObject) {
                try {
                    apkVersion = jsonObject.getString("version");
                    apkUrl = jsonObject.getString("apkurl");
                    description = jsonObject.getString("description");
                    appName = jsonObject.getString("appname");
                    if (getVersionName().equals(apkVersion)) {
                        //没有新版本,进入主页面
                        enterHome();
                    } else {
                        //发现新版本,弹出升级对话框
                        showUpdateDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 显示升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        builder.setTitle("提示升级");
        builder.setMessage("");
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAPK();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 进入主页面
     */
    private void enterHome() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    /**
     * 获取apk版本号信息
     *
     * @return
     */
    private String getVersionName() {
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(getPackageName(), 0);
        return packageInfo.versionName;
    }

    /**
     * 下载apk
     */
    private void downloadAPK() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            HttpUtils http = new HttpUtils();
            http.download(apkUrl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + appName,
                    true, true, new RequestCallBack<File>() {

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {

                        }

                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            File file = responseInfo.result.getAbsoluteFile();
                            installAPK(file);
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {

                        }

                    });
        } else {
            showToast("请确保手机安装了SD卡");
        }

    }


    /**
     * 安装apk
     *
     * @param file
     */
    private void installAPK(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
