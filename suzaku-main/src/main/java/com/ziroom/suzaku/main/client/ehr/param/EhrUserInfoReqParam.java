package com.ziroom.suzaku.main.client.ehr.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class EhrUserInfoReqParam implements Serializable {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 页大小
     */
    private Integer size;

    /**
     * seriesCode
     */
    private String seriesCode = "FUNC";
}
