package com.ziroom.suzaku.main.client.ehr.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class EhrUserSimpleInfoReqParam implements Serializable {
    private String userEmail;
    private String page = "1";
    private String size = "5";
}
