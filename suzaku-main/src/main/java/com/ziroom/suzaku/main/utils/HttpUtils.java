package com.ziroom.suzaku.main.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author libingsi
 * @date 2021/6/22 14:57
 */
public class HttpUtils {

    private static volatile HttpUtils mInstance;

    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    public String get(String url){
        return HttpUtil.get(url);
    }

    public String get(String url, Map<String, Object> paramMap){
        return HttpUtil.get(url,paramMap);
    }


    public String post(String url, Map<String, Object> paramMap){
        return HttpUtil.post(url,paramMap);
    }

    public <T> T post(String url, Map<String, Object> paramMap,Class<T> clazz){
        String res = post(url,paramMap);
        return JSON.parseObject(res,clazz);
    }

}
