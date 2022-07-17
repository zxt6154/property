package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.vo.ClassifyPropertyVo;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyPropertyVo;

import java.util.List;

/**
 * <p>
 * 资产分类表 服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
public interface SuzakuClassifyPropertyService extends IService<SuzakuClassifyProperty> {

    /**
     * 获取资产分类树状结构信息
     * @return
     */
    List<SuzakuClassifyPropertyVo> getClassifyPropertyTreeNode();

    /**
     * 获取某个资产分类信息和子资产
     * @param classifyId
     * @return
     */
    List<SuzakuClassifyPropertyVo> getClassifyPropertyInfo(String classifyId);

    SuzakuClassifyProperty getClassifyInfoById(String classifyId);

    List<SuzakuClassifyProperty> getClassifyInfos(List<String> classifyIds);

    /**
     * 保存资产分类信息
     * @param vo
     */
    void saveClassifyProperty(ClassifyPropertyVo vo);

    /**
     * 删除资产分类信息
     * @param classifyId
     */
    void deleteClassifyProperty(String classifyId);

    /**
     * 获取某个资产分类信息
     * @param classifyId
     * @return
     */
    SuzakuClassifyPropertyVo getClassifyProperty(String classifyId);

    /**
     * 判断资产分类下是否存在资产
     * @param classifyId
     */
    int checkClassifySku(String classifyId);

    /**
     * 判断资产分类名称是否存在
     * @param classifyName
     * @return
     */
    int checkClassifyName(String classifyName);
}
