package com.ziroom.suzaku.main.utils;

import io.swagger.annotations.ApiModelProperty;

import java.util.*;

/**
 * @author libingsi
 * @date 2021/6/22 10:59
 */
public class TreeNodeUtils<T> {
    @ApiModelProperty(value = "标识")
    protected String id;
    @ApiModelProperty(value = "上级标识")
    protected String parentId;
    @ApiModelProperty(value = "名称")
    protected String label;
    @ApiModelProperty(value = "子节点列表")
    protected List<T> children;

    @ApiModelProperty("是否叶子节点")
    public boolean isLeaf() {
        return this.children == null || this.children.size() == 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return label;
    }

    public void setName(String name) {
        this.label = name;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public void add(T t) {
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        children.add(t);
    }
    public static <T extends TreeNodeUtils<T>> List<T> bulid(List<T> treeNodes, String root) {

        Map<String,T> allNodes = new HashMap<>();
        for (T treeNode : treeNodes) {
            allNodes.put(treeNode.getId(),treeNode);
        }
        Map<String,T> allRoots = new LinkedHashMap<>();

        for (T treeNode : treeNodes) {
            if(StringUtils.isNotBlank(root) && treeNode.getId().equals(root)){
                allRoots.put(treeNode.getId(),treeNode);
                continue;
            }
            if(StringUtils.isBlank(treeNode.getParentId())){
                allRoots.put(treeNode.getId(),treeNode);
                continue;
            }
            if(StringUtils.isNotBlank(root)&& root.equals(treeNode.getParentId()) && !allNodes.containsKey(root)){
                allRoots.put(treeNode.getId(),treeNode);
                continue;
            }

            if(StringUtils.isNotBlank(treeNode.getParentId())){
                TreeNodeUtils<T> parentNode = allNodes.get(treeNode.getParentId());
                if(parentNode != null){
                    if (parentNode.getChildren() == null) {
                        parentNode.setChildren(new ArrayList<>());
                    }
                    parentNode.add(treeNode);
                }
            }
        }
        return new ArrayList<>(allRoots.values());
    }

}
