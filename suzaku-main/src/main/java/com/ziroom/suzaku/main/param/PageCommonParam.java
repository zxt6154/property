package com.ziroom.suzaku.main.param;

import lombok.NoArgsConstructor;

/**
 * @author xuzeyu
 */
@NoArgsConstructor
public class PageCommonParam {

    /**
     * 页码
     */
    private Integer offset =1;

    private Integer index;

    /**
     * 分页大小
     */
    private Integer pageSize =20;

    public Integer getIndex() {
        this.index = (offset==null) ? null : (offset-1)*pageSize;
        return this.index;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
