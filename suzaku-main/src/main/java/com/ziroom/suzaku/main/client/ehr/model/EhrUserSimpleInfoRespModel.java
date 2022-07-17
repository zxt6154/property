package com.ziroom.suzaku.main.client.ehr.model;

import lombok.Data;

/**
 * @author xuzeyu
 */
@Data
public class EhrUserSimpleInfoRespModel {
    //职级
    private String levelName;

    //姓名
    private String name;

    //treePath
    private String treePath;

    //dept
    private String dept;


}
