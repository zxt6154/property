package com.ziroom.suzaku.main.client.ehr.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class EhrDepartmentInfoReqParam implements Serializable {
    private String code;
}
