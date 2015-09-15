package com.dong.tourshanghai.entity;

import java.util.ArrayList;

/**
 * Intro: 新闻列表实体
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class NewsListEntity {

    public ArrayList<NewsModel> newsList;

    public static class NewsModel {
        public int news_id;             //新闻id
        public int news_type;           //类型编号—— 1.热点 2.策划 3.节庆 4.攻略
        public String news_title;          //标题
        public String news_sub;            //简介
        public String news_content;        //正文
        public String news_imgurl;         //列表图片url
        public int istop;
//        public String               news_time;           //发表时间

    }


}
