//package com.dong.tourshanghai.engine;
//
//import org.json.JSONObject;
//
///**
// * Intro:
// * <p>
// * Programmer: dong
// * Date: 15/9/6.
// */
//public class HelpEngineImpl implements HelpEngine {
//    @Override
//    public void getServerHelpList() {
//        //1.检查本地是否存储过帮助列表信息,获取本地最新的version值
//        String version = "0";
//        //2.发送version到服务器端,回复Json的字符串
//
//        //3.检查返回的数据是否正常
//        try {
//            JSONObject object = new JSONObject(json);
//            if (checkError(object)) {
//
//            } else {
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //4.对帮助列表进行数据处理
//    }
//
//    @Override
//    public void getServerHelpDetail() {
//
//    }
//
//    @Override
//    public void getLocalHelpList() {
//
//    }
//
//    @Override
//    public void getLocalHelpDetail() {
//
//    }
//}
