package com.dong.tourshanghai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.adapter.SpotsListAdapter;
import com.dong.tourshanghai.entity.SpotsListEntity;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.ui.NewsDetailActicity;
import com.dong.tourshanghai.ui.SpotsMapActivity;
import com.dong.tourshanghai.utils.JSONParser;
import com.dong.tourshanghai.view.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Intro: 景点列表
 * <p/>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment2 extends NaviBaseFragment {

    private XListView mListView;
    private ArrayList<SpotsListEntity.SpotModel> mList;
    private SpotsListAdapter mAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        getSpotsData(1, 1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("POSITION", position + "");
                Intent detailIntent = new Intent(mContext, NewsDetailActicity.class);

                detailIntent.putExtra("id", mList.get(position).spot_id);
                startActivity(detailIntent);
            }
        });

    }

    @Override
    public void setContentView(LinearLayout contentLayout) {
        View childView = View.inflate(mContext, R.layout.layout_fragment2, null);
        super.setNavigateLeftButtonIsShow(false);
        super.setNavigateRightButtonIsShow(true);
        super.setNavigateMiddleTitle("景点");
        super.setNavigateRightImageViewImage(R.mipmap.btn_maplyr);
        contentLayout.addView(childView);

        mListView = (XListView) childView.findViewById(R.id.lv_spotslist);
        mList = new ArrayList<SpotsListEntity.SpotModel>();
    }

    @Override
    public void topRightButtonClick(View v) {
        double[] listLat = new double[50];
        double[] listLon = new double[50];
        String[] listName = new String[50];
        int listNum = mList.size();
        for (int i = 0; i < listNum; i++) {
            listName[i] = mList.get(i).spot_name;
            listLat[i] = mList.get(i).spot_lat;
            listLon[i] = mList.get(i).spot_lon;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray("listName", listName);
        bundle.putDoubleArray("listLat", listLat);
        bundle.putDoubleArray("listLon", listLon);
        Intent intent = new Intent(mContext, SpotsMapActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void getSpotsData(int type, int orderBy) {
        HttpRequestVo vo = new HttpRequestVo();
        vo.requestUrl = ConstantValues.GET_SPOTS_LIST;
        vo.context = mContext;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", type + "");
        map.put("orderby", orderBy + "");
        vo.requestDataMap = map;
        vo.parser = new JSONParser(SpotsListEntity.class);
        JSONObject requestJson = new JSONObject();
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
                SpotsListEntity spotsEntity = (SpotsListEntity) paramObject.get("datamap");

                mList.addAll(spotsEntity.spotsList);

                if (mListView.getAdapter() == null) {
                    mAdapter = new SpotsListAdapter(mContext, mList);
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFinish() {

            }
        });
    }


}
