package com.dong.tourshanghai.entity;

import java.util.ArrayList;

/**
 * Intro: 景点实体类
 * <p>
 * Programmer: dong
 * Date: 15/9/3.
 */
public class SpotsListEntity {

    public ArrayList<SpotModel> spotsList;

    public static class SpotModel {

        public int spot_id; //景点id
        public int spot_type; //景点类型
        public String spot_name; //景点名称
        public double spot_lat; //景点经度
        public double spot_lon; //景点纬度
        public String spot_addr; //景点地址
        public String spot_trans; //景点交通
        public String spot_imgurl; //景点图片地址
        public String spot_intro; //景点详情介绍
        public String spot_fee; //景点门票
        public String spot_time; //景点开放时间

    }

}
