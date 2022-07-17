package com.ziroom.suzaku.main.client.uc.model;

import lombok.Data;

/**
 * @author xuzeyu
 */
@Data
public class UcBaseRespModel<T> {
    private String code;
    private String message;
    private String sys;
    private T data;

}
