package com.dong.tourshanghai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.adapter.SpotsListAdapter;
import com.dong.tourshanghai.entity.SpotsListEntity;
import com.dong.tourshanghai.net.HttpManager;
import com.dong.tourshanghai.net.HttpRequestVo;
import com.dong.tourshanghai.ui.NewsDetailActicity;
import com.dong.tourshanghai.utils.JSONParser;
import com.dong.tourshanghai.view.MyTitlebar;
import com.dong.tourshanghai.view.XListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Intro: 景点列表
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class Fragment2 extends BaseFragment {

    private XListView mListView;
    private ArrayList<SpotsListEntity.SpotModel> mList;
    private SpotsListAdapter mAdapter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_fragment2, null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        initTitlebarForRight("景点", R.mipmap.tab_item_more, new MyTitlebar.OnHeaderButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {

            }
        });

        mListView = (XListView) view.findViewById(R.id.lv_spotslist);
        mList = new ArrayList<SpotsListEntity.SpotModel>();

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
                ArrayList<SpotsListEntity.SpotModel> spotsList = spotsEntity.spotsList;
                mList.addAll(spotsList);

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
