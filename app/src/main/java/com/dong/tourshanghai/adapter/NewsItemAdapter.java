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
import com.dong.tourshanghai.entity.NewsListEntity;
import com.dong.tourshanghai.utils.BitmapCache;

import java.util.List;

/**
 * Intro: 新闻列表适配器
 * <p>
 * Programmer: dong
 * Date: 15/9/7.
 */
public class NewsItemAdapter extends BaseAdapter {

    private List<NewsListEntity.NewsModel> mList;
    private LayoutInflater mInflater;

    public NewsItemAdapter(Context context, List<NewsListEntity.NewsModel> list) {
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
        NewsListEntity.NewsModel newsModel = mList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_newslist, null);
            holder = new ViewHolder();
            holder.newsIcon = (NetworkImageView) convertView.findViewById(R.id.niv_newsicon);
            holder.newsTitle = (TextView) convertView.findViewById(R.id.tv_newstitle);
            holder.subTitle = (TextView) convertView.findViewById(R.id.tv_newssub);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.newsTitle.setText(newsModel.news_title);
        holder.subTitle.setText(newsModel.news_sub);

        //使用ImageLoader请求网络图片
        String url = ConstantValues.PIC_URL_NEWS + newsModel.news_imgurl;
        ImageLoader loader = new ImageLoader(CustomApplication.getHttpQueue(), new BitmapCache());
        holder.newsIcon.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.newsIcon.setErrorImageResId(R.mipmap.ic_launcher);
        holder.newsIcon.setImageUrl(url, loader);

        return convertView;
    }

    private class ViewHolder {
        NetworkImageView newsIcon;
        TextView newsTitle;
        TextView subTitle;
    }
}
