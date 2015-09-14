package com.dong.tourshanghai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.dong.tourshanghai.ConstantValues;
import com.dong.tourshanghai.CustomApplication;
import com.dong.tourshanghai.R;
import com.dong.tourshanghai.entity.SpotsListEntity;
import com.dong.tourshanghai.utils.BitmapCache;

import java.util.List;

/**
 * Intro:
 * <p>
 * Programmer: dong
 * Date: 15/9/7.
 */
public class SpotsListAdapter extends BaseAdapter {
    private List<SpotsListEntity.SpotModel> mList;
    private LayoutInflater mInflater;

    public SpotsListAdapter(Context context, List<SpotsListEntity.SpotModel> list) {
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        SpotsListEntity.SpotModel spotModel = mList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_spotslist, null);
            holder.spotIcon = (NetworkImageView) convertView.findViewById(R.id.niv_spoticon);
            holder.spotName = (TextView) convertView.findViewById(R.id.tv_spotname);

            String url = ConstantValues.PIC_URL_SPOTS + spotModel.spot_imgurl;
            ImageLoader loader = new ImageLoader(CustomApplication.getHttpQueue(), new BitmapCache());
            holder.spotIcon.setDefaultImageResId(R.mipmap.ic_launcher);
            holder.spotIcon.setErrorImageResId(R.mipmap.ic_launcher);
            holder.spotIcon.setImageUrl(url, loader);

        }
        return convertView;
    }

    private class ViewHolder {
        NetworkImageView spotIcon;
        TextView spotName;
        TextView spot;
    }
}
