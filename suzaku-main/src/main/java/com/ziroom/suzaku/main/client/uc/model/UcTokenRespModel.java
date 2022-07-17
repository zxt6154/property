package com.ziroom.suzaku.main.client.uc.model;

import lombok.Data;

/**
 * @author xuzeyu
 */
@Data
public class UcTokenRespModel {

    /**
     * token
     */
    private String access_token;

    /**
     * token_type
     */
    private String token_type;

    /**
     * 过期时间
     */
    private Integer expires_in;

    /**
     * scope
     */
    private String scope;
}
