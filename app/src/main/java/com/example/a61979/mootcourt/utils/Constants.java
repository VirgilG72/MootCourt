package com.example.a61979.mootcourt.utils;

/**
 * 作者：尚硅谷-杨光福 on 2016/8/15 11:44
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：常量类，配置联网请求地址
 */
public class Constants {

    /**
     * 联网请求的ip和端口
     */
    public static final String BASE_URL = "http://192.168.43.157:8080/web_home";//手机热点Virgil
    //public static final String BASE_URL = "http://172.18.174.255:8080/web_home";//图书馆SCAUNET2
  // public static final String BASE_URL = "http://192.168.1.105:8080/web_home";//宿舍hacker


//    public static final String BASE_URL = "http://10.0.2.2:8080/web_home";

    /**
     * 新闻中心的网络地址
     */
    public static final String DISCOVER_PAGER_URL = BASE_URL+"/static/api/news/categories.json";

    /**
     * 商品热卖
     */
    public static final String WARES_HOT_URL = "http://112.124.22.238:8081/course_api/wares/hot?pageSize=";
}
