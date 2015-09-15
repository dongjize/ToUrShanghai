package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.entity.SpotsListEntity;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.utils.JSONParser;
import com.dong.tourshanghai.view.MyTitlebar;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Intro:
 * <p/>
 * Programmer: dong
 * Date: 15/9/12.
 */
public class SpotDetailActivity extends BaseActivity {

    private MyTitlebar.OnHeaderButtonClickListener listener;
    private ImageView ivSpotImg;
    private TextView tvSpotName, tvSpotAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_spot_detail);

        initTitlebarForBoth("", R.mipmap.btn_back, R.mipmap.ic_launcher, new MyTitlebar.OnHeaderButtonClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                Toast.makeText(SpotDetailActivity.this, "地图功能...待实现", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getSpotDetailData(int spot_id) {
        HttpRequestVo vo = new HttpRequestVo();
        vo.requestUrl = ConstantValues.GET_SPOT_DETAIL;
        vo.context = mContext;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("spot_id", spot_id + "");
        vo.requestDataMap = map;
        JSONObject requestJson = new JSONObject();
        vo.parser = new JSONParser(SpotsListEntity.class);
        vo.requestJson = requestJson;

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
}
