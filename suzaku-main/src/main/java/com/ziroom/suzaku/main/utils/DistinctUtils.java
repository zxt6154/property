package com.ziroom.suzaku.main.utils;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xuzeyu
 */
@Slf4j
public class DistinctUtils {

    /**
     * 去重
     */
    public static <T> Predicate<T> distinctByKey(Function<T, ?> keyExtractor) {
        Map<Object, Boolean> map = new HashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
