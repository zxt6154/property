package com.ziroom.suzaku.main.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;

/**
 * @author libingsi
 * @since 2021-10-14
 */
public class DozerUtil {

    public static <FROM, TO> PageData<TO> mapPageData(IPage<FROM> fromPage, final Class<TO> toClass, BeanMapper mapper) {
        PageData<TO> toPage = new PageData<TO>();
        toPage.setTotal(fromPage.getTotal());
        toPage.setPages(fromPage.getPages());
        toPage.setPageNum(fromPage.getCurrent());
        toPage.setPageSize(fromPage.getSize());
        if (fromPage.getRecords() != null) {
            toPage.setData(mapper.mapList(fromPage.getRecords(), toClass));
        }
        return toPage;
    }

}
