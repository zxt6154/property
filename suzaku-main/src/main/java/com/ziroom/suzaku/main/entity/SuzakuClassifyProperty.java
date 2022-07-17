package com.ziroom.suzaku.main.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资产分类表
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuClassifyProperty对象", description="资产分类表")
public class SuzakuClassifyProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "分类编码")
    private String classifyCode;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelProperty(value = "上级分类id")
    private String seniorId;

    @ApiModelProperty(value = "计量单位")
    private String classifyUnit;

    @ApiModelProperty(value = "预计使用期限")
    private Integer classifyDeadline;

    @ApiModelProperty(value = "操作人工号")
    private String operatorCode;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "tree_code")
    private String treeCode;

    @ApiModelProperty(value = "是否删除（0：未删除 1：软删除）")
    private String deleteFlag;

    @ApiModelProperty(value = "code_path")
    private String codePath;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


    public static void main(String[] args) {

        List<String> list1 = new ArrayList<>();
        list1.add("110");
        list1.add("120");
        list1.add("130");

        List<String> list2 = new ArrayList<>();
        list2.add("110");
        list2.add("130");
        list2.add("140");

        List<String> list3 = new ArrayList<>();
        list3.add("110");
        list3.add("120");

        List<List<String>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        Optional<List<String>> list = lists.parallelStream()
                .filter(elementList -> elementList != null && ((List) elementList).size() != 0)
                .reduce((a, b) -> {
                    a.retainAll(b);
                    return a;
                });
        List<String> list4 = list.get();
        System.out.println(list4);
    }
}
