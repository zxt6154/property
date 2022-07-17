package com.ziroom.suzaku.main.model.vo;

import com.ziroom.suzaku.main.utils.TreeNodeUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @author libingsi
 * @date 2021/10/9 16:34
 */
@Getter
@Setter
@ApiModel(value="SuzakuClassifyProperty对象", description="资产分类表")
public class SuzakuClassifyPropertyVo extends TreeNodeUtils {

    @ApiModelProperty("分类编码")
    private String classifyCode;

    @ApiModelProperty("上级分类")
    private String parentName;

    @ApiModelProperty("级联菜单id")
    private List<String> path;

    @ApiModelProperty(value = "计量单位")
    private String classifyUnit;

    @ApiModelProperty(value = "预计使用期限")
    private Integer classifyDeadline;

    @ApiModelProperty("扩展字段")
    private List<SuzakuClassifyCustomVo> classifyCustomVos;

    @ApiModelProperty("是否叶子节点")
    @Override
    public boolean isLeaf() {
        return this.children == null || this.children.size() == 0;
    }

}
