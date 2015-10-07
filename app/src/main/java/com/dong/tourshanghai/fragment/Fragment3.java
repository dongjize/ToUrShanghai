package com.dong.tourshanghai.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.ui.BlankActivity;
import com.dong.tourshanghai.ui.FeedbackActivity;
import com.dong.tourshanghai.ui.LoginActivity;
import com.dong.tourshanghai.utils.ClearCacheUtils;
import com.dong.tourshanghai.utils.SharedPreferencesUtils;
import com.dong.tourshanghai.view.CircleImageView;

import java.io.File;

/**
 * Intro: 更多
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment3 extends BaseFragment implements View.OnClickListener {

    private RelativeLayout clearCache, aboutUs, feedBack, shareFriends, logout;
    private CircleImageView userIcon;
    private TextView userName;
    private Button btnLogin;
    private File iconFile;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setListeners();

        SharedPreferencesUtils sp = new SharedPreferencesUtils(mContext, "userIcon");
        String uriStr = sp.getUserIconURI();
        if (uriStr != null) {
            userIcon.setImageURI(Uri.parse(uriStr));
        } else {
            userIcon.setImageResource(R.mipmap.ic_launcher);
        }

    }

    @Override
    public void setContentView(LinearLayout contentLayout) {
        View childView = View.inflate(mContext, R.layout.layout_fragment3, null);

        clearCache = (RelativeLayout) childView.findViewById(R.id.setting_clear_cache);
        aboutUs = (RelativeLayout) childView.findViewById(R.id.setting_about);
        feedBack = (RelativeLayout) childView.findViewById(R.id.setting_feedback);
        shareFriends = (RelativeLayout) childView.findViewById(R.id.setting_share);
        logout = (RelativeLayout) childView.findViewById(R.id.setting_logout);
        userIcon = (CircleImageView) childView.findViewById(R.id.iv_user_icon);
        userName = (TextView) childView.findViewById(R.id.tv_user_name);
        btnLogin = (Button) childView.findViewById(R.id.btn_login);

        contentLayout.addView(childView);
    }

    private void setListeners() {
        clearCache.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        shareFriends.setOnClickListener(this);
        logout.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_icon:
                uploadPhoto();

                break;
            case R.id.btn_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.animator.anim_activity_change, R.animator.anim_activity_exit);
                break;
            case R.id.setting_collection:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.animator.anim_activity_change, R.animator.anim_activity_exit);
                break;
            case R.id.setting_clear_cache:
                clearCache();
                break;
            case R.id.setting_logout:
                logOut();
                break;
            case R.id.setting_about:
                showAboutUs();
                break;
            case R.id.setting_feedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logOut() {
        showToast("logOut");
    }

    /**
     * 设置头像(头像要上传到服务器,与user表联系起来)
     */
    private void uploadPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("添加头像");
        final String[] methods = new String[]{"拍照", "从相册中选择"};
        builder.setItems(methods, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        getImageFromCamera();
                        break;
                    case 1:
                        getImageFromAlbum();
                        break;
                }
            }
        });
        builder.show();
    }

    /**
     * 调用系统照相机拍照并上传头像
     */
    private void getImageFromCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        iconFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(iconFile)); //指定保存文件的路径
        startActivityForResult(intent, 200);
    }

    /**
     * 获取图库中的图片
     */
    private void getImageFromAlbum() {
        //打开图库
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);

    }

    /**
     * 获取返回的图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        if (requestCode == 100) { // 从相册中获取
            if (data != null) {
                uri = data.getData();
                userIcon.setImageURI(uri); //可以直接设置set uri来显示图片
            }
        } else if (requestCode == 200) { //调用照相机拍照
            uri = Uri.fromFile(iconFile);
            userIcon.setImageURI(uri);
        }
        if (uri != null) {
            SharedPreferencesUtils sp = new SharedPreferencesUtils(mContext, "userIcon");
            sp.setUserIconURI(uri);
        }


    }


    private void clearCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("确定清除缓存?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClearCacheUtils.cleanSharedPreference(mContext);
                ClearCacheUtils.cleanInternalCache(mContext);
                ClearCacheUtils.cleanFiles(mContext);
                Toast.makeText(mContext, "清理完成", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void showAboutUs() {
        Intent intent = new Intent(getActivity(), BlankActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("layoutID", R.layout.layout_about);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
