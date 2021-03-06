package com.ziroom.suzaku.main.common;

import cn.hutool.core.collection.CollUtil;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Maps;
import com.ziroom.suzaku.main.utils.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author libingsi
 * @date 2021/6/22 15:37
 */
public class BeanMapper {

    private Mapper mapper;

    public BeanMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }

    public <T> T map(Object source, Class<T> destinationClass)
    {
        if (source == null) {
            return null;
        }
        return (T)this.mapper.map(source, destinationClass);
    }

    public <T> T map(Object source, Class<T> destinationClass, Set<String> includes)
    {
        T bean = map(source, destinationClass);
        if (!CollUtil.isEmpty(includes))
        {
            Map<String, Object> map = beanToMap(bean, includes);
            bean = mapToBean(map, bean);
        }
        return bean;
    }

    public <FROM, TO> List<TO> mapList(List<FROM> fromList, Class<TO> toClass)
    {
        if (fromList == null) {
            return null;
        }
        List<TO> ls = new ArrayList(fromList.size());
        for (FROM f : fromList) {
            ls.add(this.mapper.map(f, toClass));
        }
        return ls;
    }

    public <FROM, TO> List<TO> mapList(List<FROM> fromList, Class<TO> toClass, Set<String> includes)
    {
        if (fromList == null) {
            return null;
        }
        List<TO> ls = new ArrayList(fromList.size());
        for (FROM f : fromList) {
            ls.add(map(f, toClass, includes));
        }
        return ls;
    }

    public <FROM, TO> PageData<TO> mapPageData(PageData<FROM> fromPage, Class<TO> toClass)
    {
        if (fromPage == null) {
            return null;
        }
        PageData<TO> toPage = new PageData();
        toPage.setTotal(fromPage.getTotal());
        toPage.setPages(fromPage.getPages());
        toPage.setPageNum(fromPage.getPageNum());
        toPage.setPageSize(fromPage.getPageSize());
        if (fromPage.getData() != null) {
            toPage.setData(mapList(fromPage.getData(), toClass));
        }
        return toPage;
    }

    public <FROM, TO> PageData<TO> mapPageData(PageData<FROM> fromPage, Class<TO> toClass, Set<String> includes)
    {
        if (fromPage == null) {
            return null;
        }
        PageData<TO> toPage = new PageData();
        toPage.setTotal(fromPage.getTotal());
        toPage.setPages(fromPage.getPages());
        toPage.setPageNum(fromPage.getPageNum());
        toPage.setPageSize(fromPage.getPageSize());
        if (fromPage.getData() != null) {
            toPage.setData(mapList(fromPage.getData(), toClass, includes));
        }
        return toPage;
    }

    public static <T> Map<String, Object> beanToMap(T bean)
    {
        Map<String, Object> map = Maps.newHashMap();
        BeanMap beanMap;
        if (bean != null)
        {
            beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if ((key != null) && (beanMap.get(key) != null)) {
                    map.put(StringUtils.toString(key), beanMap.get(key));
                }
            }
        }
        return map;
    }

    public static <T> Map<String, Object> beanToMap(T bean, Set<String> includes)
    {
        Map<String, Object> map = Maps.newHashMap();
        BeanMap beanMap;
        if (bean != null)
        {
            beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if ((key != null) && (beanMap.get(key) != null) && (includes.contains(StringUtils.toString(key)))) {
                    map.put(StringUtils.toString(key), beanMap.get(key));
                }
            }
        }
        return map;
    }

    public static <T> T mapToBean(Map<String, Object> map, T bean)
    {
        T obj;
        try
        {
            obj = (T) bean.getClass().newInstance();
        }
        catch (Exception e)
        {
            obj = bean;
        }
        BeanMap beanMap = BeanMap.create(obj);
        beanMap.putAll(map);
        return obj;
    }
}
