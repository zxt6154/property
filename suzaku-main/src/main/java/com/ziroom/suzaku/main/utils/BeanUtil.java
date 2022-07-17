package com.ziroom.suzaku.main.utils;

import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanMap;

import java.util.Map;

/**
 * @author xuzeyu
 */
public class BeanUtil {

    /**
     * 将对象转换为 Map<String,String>
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, String> beanToMap(T bean) {
        Map<String, String> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key).toString());
            }
        }
        return map;
    }

    /**
     * 将map转换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
}
