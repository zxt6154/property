package com.ziroom.suzaku.main.client.ehr.model;

import lombok.Data;

/**
 * @author xuzeyu
 */
@Data
public class EhrBaseRespModel<T> {
    private String status;
    private Integer errorCode;
    private String message;
    private Integer totleCount;
    private T data;

}
