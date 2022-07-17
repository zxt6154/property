package com.ziroom.suzaku.main.client.ehr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class EhrInfoRespModel {

    private String fullName;

    private String email;

    private String empCode;

    private String value;

}
