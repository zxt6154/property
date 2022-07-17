package com.ziroom.suzaku.main.client.uc.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class UcUserInfoReqParam implements Serializable {

    /**
     * organUid
     */
    private String organUid = "d43f55be14b411ea891c005056ac6c04";

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * pageSize
     */
    private Integer pageSize = 1000;

}
