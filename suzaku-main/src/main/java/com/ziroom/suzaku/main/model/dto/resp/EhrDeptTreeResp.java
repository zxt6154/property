package com.ziroom.suzaku.main.model.dto.resp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EhrDeptTreeResp {

    //部门名称
    private String label;

    //部门系统号
    private String value;

    //是否是叶子部门
    private boolean isLeaf;

    //是否是叶子部门
    private String treeDeep;

    //子部门列表
    private List<EhrDeptTreeResp> children = new ArrayList<>();
}