package com.ziroom.suzaku.main.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * @author libingsi
 * @date 2021/6/22 10:59
 */
@ApiModel(description= "分页数据")
public class PageData<T> {
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private long pageNum=1;
    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数")
    private long pageSize=10;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总记录数")
    private long total=0;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private long pages=1;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private List<T> data = new ArrayList<T>();

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (this.pageSize != 0) {
            this.pages = total % pageSize > 0 ? total / pageSize + 1 : total / pageSize;
        }
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", data=" + data +
                '}';
    }
}
