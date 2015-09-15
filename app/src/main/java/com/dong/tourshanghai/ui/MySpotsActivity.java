package com.dong.tourshanghai.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.dong.tourshanghai.R;
import com.dong.tourshanghai.adapter.SpotsListAdapter;
import com.dong.tourshanghai.db.SpotsCollectionDao;
import com.dong.tourshanghai.entity.SpotsListEntity;

import java.util.ArrayList;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/14.
 */
public class MySpotsActivity extends BaseActivity {

    private ListView mListView;
    private ArrayList<SpotsListEntity.SpotModel> mList;
    private SpotsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myspots);
        mListView = (ListView) findViewById(R.id.lv_myspots);
        mList = new ArrayList<SpotsListEntity.SpotModel>();

        initView();

    }

    private void initView() {

        SpotsCollectionDao dao = new SpotsCollectionDao(mContext);
        ArrayList<SpotsListEntity.SpotModel> spotsList = dao.queryAll();

        if (mListView.getAdapter() == null) {
            mAdapter = new SpotsListAdapter(mContext, spotsList);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }
}
