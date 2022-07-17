package com.ziroom.suzaku.main.client.uc.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class UcTokenReqParam implements Serializable {

    /**
     * client_id
     */
    private String client_id = "omega_client_credentials";

    /**
     * client_secret
     */
    private String client_secret = "c18d906e314b111ea891c005056ac604";

    /**
     * grant_type
     */
    private String grant_type = "client_credentials";
}
