package com.ziroom.suzaku.main.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.entity.SuzakuClassifyCustom;
import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
import com.ziroom.suzaku.main.dao.SuzakuClassifyPropertyMapper;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.model.vo.ClassifyPropertyVo;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyCustomVo;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyPropertyVo;
import com.ziroom.suzaku.main.service.SuzakuClassifyCustomService;
import com.ziroom.suzaku.main.service.SuzakuClassifyPropertyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.service.SuzakuSkuService;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import com.ziroom.suzaku.main.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.ziroom.suzaku.main.constant.SuzakuConstant.DELETE_FLAG;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 资产分类表 服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Service
@Slf4j
public class SuzakuClassifyPropertyServiceImpl extends ServiceImpl<SuzakuClassifyPropertyMapper, SuzakuClassifyProperty> implements SuzakuClassifyPropertyService {

    @Resource
    private SuzakuClassifyPropertyMapper suzakuClassifyPropertyMapper;

    @Autowired
    private SuzakuClassifyCustomService suzakuClassifyCustomService;

    @Autowired
    private SuzakuSkuService suzakuSkuService;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private UserHolder userHolder;

    @Override
    public List<SuzakuClassifyPropertyVo> getClassifyPropertyTreeNode() {
        List<SuzakuClassifyProperty> selectList = suzakuClassifyPropertyMapper.selectAllData();
        if (CollectionUtil.isEmpty(selectList)){
            return new ArrayList<>();
        }
        List<SuzakuClassifyPropertyVo> suzakuClassifyPropertyVos = new ArrayList<>();
        selectList.forEach(v->{
            SuzakuClassifyPropertyVo treeNode = new SuzakuClassifyPropertyVo();
            treeNode.setId(v.getId());
            treeNode.setName(v.getClassifyName());
            treeNode.setParentId(v.getSeniorId());
            treeNode.setClassifyCode(v.getClassifyCode());
            suzakuClassifyPropertyVos.add(treeNode);
        });
        List<SuzakuClassifyPropertyVo> tree = SuzakuClassifyPropertyVo.bulid(suzakuClassifyPropertyVos, "0");
        SuzakuClassifyPropertyVo root = new SuzakuClassifyPropertyVo();
        root.setChildren(tree);
        return root.getChildren();
    }

    @Override
    public List<SuzakuClassifyPropertyVo> getClassifyPropertyInfo(String classifyId) {
        List<SuzakuClassifyProperty> classifyPropertyList = suzakuClassifyPropertyMapper.getClassifyPropertyAndChildren(classifyId);
        if (CollectionUtil.isEmpty(classifyPropertyList)){
            return new ArrayList<>();
        }
        List<SuzakuClassifyPropertyVo> vos = new ArrayList<>();
        classifyPropertyList.forEach(v ->{
            SuzakuClassifyPropertyVo classifyPropertyVo = new SuzakuClassifyPropertyVo();
            classifyPropertyVo.setId(v.getId());
            classifyPropertyVo.setClassifyCode(v.getClassifyCode());
            classifyPropertyVo.setName(v.getClassifyName());
            SuzakuClassifyProperty classifyProperty = suzakuClassifyPropertyMapper.selectById(v.getSeniorId());
            if (ObjectUtil.isNotEmpty(classifyProperty) && StrUtil.isNotEmpty(classifyProperty.getClassifyName())){
                classifyPropertyVo.setParentName(classifyProperty.getClassifyName());
            }
            classifyPropertyVo.setClassifyUnit(v.getClassifyUnit());
            classifyPropertyVo.setClassifyDeadline(v.getClassifyDeadline());
            List<SuzakuClassifyCustomDto> classifyCustomDtos = suzakuClassifyCustomService.getClassifyCustomByClassifyId(String.valueOf(v.getId()));
            List<SuzakuClassifyCustomVo> suzakuClassifyCustomVos = beanMapper.mapList(classifyCustomDtos, SuzakuClassifyCustomVo.class);
            classifyPropertyVo.setClassifyCustomVos(suzakuClassifyCustomVos);
            vos.add(classifyPropertyVo);
        });
        return vos;
    }

    @Override
    public SuzakuClassifyProperty getClassifyInfoById(String classifyId) {
        return suzakuClassifyPropertyMapper.getClassInfoById(classifyId);
    }

    @Override
    public List<SuzakuClassifyProperty> getClassifyInfos(List<String> classifyIds) {
        return suzakuClassifyPropertyMapper.getClassifyInfos(classifyIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveClassifyProperty(ClassifyPropertyVo vo) {
        if(null != vo.getId()){
            updateClassifyProperty(vo);
            return;
        }
        SuzakuClassifyProperty classifyProperty = new SuzakuClassifyProperty();
        classifyProperty.setId(UUIDUtil.getId());
        classifyProperty.setClassifyName(vo.getClassifyName());
        classifyProperty.setOperatorCode(userHolder.getUserInfo().getUid());
        classifyProperty.setOperatorName(userHolder.getUserInfo().getName());
        classifyProperty.setClassifyUnit(vo.getClassifyUnit());
        classifyProperty.setClassifyDeadline(vo.getClassifyDeadline());
        classifyProperty.setDeleteFlag(DELETE_FLAG);
        if (StrUtil.isNotEmpty(vo.getSeniorId())){
            classifyProperty.setSeniorId(getSeniorId(vo.getSeniorId()));
            classifyProperty.setClassifyCode(initCode(getSeniorId(vo.getSeniorId())));
        }else {
            classifyProperty.setClassifyCode(initCode(null));
        }
        suzakuClassifyPropertyMapper.insert(classifyProperty);
        //提交事务更新tree_code ，分类扩展属性
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                updateTreeCode(vo);
                if (CollectionUtil.isNotEmpty(vo.getCustomVos())){
                    createCustom(vo.getCustomVos());
                }
            }

            private void updateTreeCode(ClassifyPropertyVo vo) {
                if (StrUtil.isNotBlank(vo.getSeniorId())) {
                    SuzakuClassifyProperty suzakuClassifyProperty = suzakuClassifyPropertyMapper.selectById(classifyProperty.getSeniorId());
                    if (ObjectUtil.isNotEmpty(suzakuClassifyProperty)) {
                        classifyProperty.setTreeCode(suzakuClassifyProperty.getTreeCode() + classifyProperty.getId() + "/");
                        classifyProperty.setCodePath(suzakuClassifyProperty.getCodePath() + classifyProperty.getClassifyCode() + "/");
                    }
                } else {
                    classifyProperty.setTreeCode(classifyProperty.getId() + "/");
                    classifyProperty.setCodePath(classifyProperty.getClassifyCode() + "/");
                }
                suzakuClassifyPropertyMapper.updateById(classifyProperty);
            }

            private void createCustom(List<SuzakuClassifyCustomVo> customVos) {
                customVos.stream().filter(x -> StrUtil.isNotEmpty(x.getCustomName())).forEach(v->{
                    SuzakuClassifyCustom classifyCustom = new SuzakuClassifyCustom();
                    classifyCustom.setClassifyId(classifyProperty.getId());
                    classifyCustom.setCustomName(v.getCustomName());
                    classifyCustom.setRequiredFlag(v.getRequiredFlag());
                    classifyCustom.setSearchFlag(v.getSearchFlag());
                    classifyCustom.setOperatorCode(userHolder.getUserInfo().getUid());
                    classifyCustom.setOperatorName(userHolder.getUserInfo().getName());
                    suzakuClassifyCustomService.save(classifyCustom);
                });
            }
        });
    }

    /**
     * 更新资产分类信息
     * @param vo
     */
    private void updateClassifyProperty(ClassifyPropertyVo vo) {
        SuzakuClassifyProperty classifyProperty = new SuzakuClassifyProperty();
        classifyProperty.setId(vo.getId());
        classifyProperty.setClassifyName(vo.getClassifyName());
        classifyProperty.setOperatorCode(userHolder.getUserInfo().getUid());
        classifyProperty.setOperatorName(userHolder.getUserInfo().getName());
        classifyProperty.setClassifyUnit(vo.getClassifyUnit());
        classifyProperty.setClassifyDeadline(vo.getClassifyDeadline());
        classifyProperty.setDeleteFlag(DELETE_FLAG);
        if (StrUtil.isNotEmpty(vo.getSeniorId())){
            classifyProperty.setSeniorId(getParentSeniorId(vo.getSeniorId()));
            SuzakuClassifyProperty property = suzakuClassifyPropertyMapper.selectById(vo.getId());
            if (StrUtil.isNotEmpty(property.getSeniorId()) && !property.getSeniorId().equals(getParentSeniorId(vo.getSeniorId()))){
                classifyProperty.setClassifyCode(initCode(getParentSeniorId(vo.getSeniorId())));
            }
            SuzakuClassifyProperty suzakuClassifyProperty = suzakuClassifyPropertyMapper.selectById(getParentSeniorId(vo.getSeniorId()));
            if (ObjectUtil.isNotEmpty(suzakuClassifyProperty)) {
                classifyProperty.setTreeCode(suzakuClassifyProperty.getTreeCode() + classifyProperty.getId() + "/");
                classifyProperty.setCodePath(suzakuClassifyProperty.getCodePath() + classifyProperty.getClassifyCode() + "/");
            }
        }else {
            classifyProperty.setTreeCode(classifyProperty.getId() + "/");
            classifyProperty.setCodePath(classifyProperty.getClassifyCode() + "/");
            SuzakuClassifyProperty suzakuClassifyProperty = suzakuClassifyPropertyMapper.selectById(vo.getId());
            classifyProperty.setClassifyCode(suzakuClassifyProperty.getClassifyCode());
        }
        suzakuClassifyPropertyMapper.updateById(classifyProperty);

        if (CollectionUtil.isNotEmpty(vo.getCustomVos())){
            vo.getCustomVos().stream().filter(x -> StrUtil.isNotEmpty(x.getCustomName())).forEach(v->{
                SuzakuClassifyCustom classifyCustom = new SuzakuClassifyCustom();
                classifyCustom.setId(v.getId());
                classifyCustom.setClassifyId(classifyProperty.getId());
                classifyCustom.setCustomName(v.getCustomName());
                classifyCustom.setRequiredFlag(v.getRequiredFlag());
                classifyCustom.setSearchFlag(v.getSearchFlag());
                classifyCustom.setOperatorCode(userHolder.getUserInfo().getUid());
                classifyCustom.setOperatorName(userHolder.getUserInfo().getName());
                suzakuClassifyCustomService.saveOrUpdate(classifyCustom);
            });
            deleteClassifyCustom(classifyProperty.getId(),vo.getCustomVos());
        }else {
            List<SuzakuClassifyCustomDto> classifyCustomByClassifyId = suzakuClassifyCustomService.getClassifyCustomByClassifyId(classifyProperty.getId());
            if (CollectionUtil.isNotEmpty(classifyCustomByClassifyId)){
                classifyCustomByClassifyId.forEach(v -> {suzakuClassifyCustomService.removeById(v.getId());});
            }
        }
    }


    @Override
    public void deleteClassifyProperty(String classifyId) {
        List<SuzakuClassifyProperty> classifyPropertyList = suzakuClassifyPropertyMapper.getClassifyPropertyAndChildren(classifyId);
        if (CollectionUtil.isEmpty(classifyPropertyList)){
            return;
        }
        classifyPropertyList.forEach( v-> {
            suzakuClassifyPropertyMapper.deleteById(v.getId());
        });
    }

    private boolean checkHaveSku(List<SuzakuClassifyProperty> classifyPropertyList) {
        AtomicBoolean isHave = new AtomicBoolean(false);
        classifyPropertyList.forEach(v -> {
            List<SuzakuSkuEntity> suzakuSkuEntities = suzakuSkuService.haveSkuByCategoryId(String.valueOf(v.getId()));
            if (CollectionUtil.isNotEmpty(suzakuSkuEntities)){
                isHave.set(true);
            }
        });
        return isHave.get();
    }

    /**
     * 兼容前端级联组件传数组上级信息
     * @param seniorId
     * @return
     */
    private String getSeniorId(String seniorId) {
        if (seniorId.contains(",")){
            ArrayList<String> list= new ArrayList(Arrays.asList(seniorId.split(",")));
            seniorId = list.get(list.size() - 1);
        }
        return seniorId;
    }


    @Override
    public SuzakuClassifyPropertyVo getClassifyProperty(String classifyId) {
        SuzakuClassifyProperty suzakuClassifyProperty = suzakuClassifyPropertyMapper.selectById(classifyId);
        if (ObjectUtil.isEmpty(suzakuClassifyProperty)){
            return null;
        }
        SuzakuClassifyPropertyVo classifyPropertyVo = beanMapper.map(suzakuClassifyProperty, SuzakuClassifyPropertyVo.class);
        List<SuzakuClassifyCustomDto> classifyCustomDtos = suzakuClassifyCustomService.getClassifyCustomByClassifyId(String.valueOf(suzakuClassifyProperty.getId()));
        List<SuzakuClassifyCustomVo> suzakuClassifyCustomVos = beanMapper.mapList(classifyCustomDtos, SuzakuClassifyCustomVo.class);
        classifyPropertyVo.setClassifyCustomVos(suzakuClassifyCustomVos);
        classifyPropertyVo.setName(suzakuClassifyProperty.getClassifyName());
        classifyPropertyVo.setClassifyCode(suzakuClassifyProperty.getClassifyCode());
        if (StrUtil.isNotEmpty(suzakuClassifyProperty.getTreeCode())){
            List<String> treeCode = Arrays.stream(suzakuClassifyProperty.getTreeCode().split("/")).collect(toList());
            classifyPropertyVo.setPath(treeCode);
        }
        return classifyPropertyVo;
    }

    @Override
    public int checkClassifySku(String classifyId) {
        List<SuzakuClassifyProperty> classifyPropertyList = suzakuClassifyPropertyMapper.getClassifyPropertyAndChildren(classifyId);
        if (CollectionUtil.isEmpty(classifyPropertyList)){
            return 1;
        }
        if (checkHaveSku(classifyPropertyList)){
            return 0;
        }
        return 1;
    }

    /**
     * 修改时找到当前修改分类的上级
     * @param seniorId
     * @return
     */
    private String getParentSeniorId(String seniorId) {
        if (seniorId.contains(",")){
            ArrayList<String> list= new ArrayList(Arrays.asList(seniorId.split(",")));
            if (list.size() == 2 || list.size() ==3){
                seniorId = list.get(list.size() - 2);
            }
        }
        return seniorId;
    }

    /**
     * 初始化code
     * @param seniorId
     * @return
     */
    private String initCode(String seniorId) {
        int number = 1;
        if (StrUtil.isEmpty(seniorId)){
            String max = suzakuClassifyPropertyMapper.selectRootMax();
            if (StrUtil.isEmpty(max)){
                return String.format("%02d", number);
            }
            return addString(max);
        }else {
            SuzakuClassifyProperty suzakuClassifyProperty = suzakuClassifyPropertyMapper.selectById(seniorId);
            String max = suzakuClassifyPropertyMapper.selectChildrenMax(seniorId);
            if (StrUtil.isEmpty(max)){
                return suzakuClassifyProperty.getClassifyCode() + String.format("%02d", number);
            }
            String split = max.substring(suzakuClassifyProperty.getClassifyCode().length());
            return suzakuClassifyProperty.getClassifyCode() + addString(split);
        }
    }

    /**
     * 递增
     * @param split
     * @return
     */
    private String addString(String split){
        String strHao ="";
        int intHao = Integer.parseInt(split);
        intHao++;
        strHao = Integer.toString(intHao);
        if (Integer.parseInt(strHao) < 10){
            strHao = "0" + strHao;
        }
        return strHao;
    }


    @Override
    public int checkClassifyName(String classifyName) {
        LambdaQueryWrapper<SuzakuClassifyProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuClassifyProperty::getClassifyName,classifyName);
        List<SuzakuClassifyProperty> classifyPropertyList = suzakuClassifyPropertyMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(classifyPropertyList)){
            return 0;
        }
        return 1;
    }

    /**
     * 删除扩展信息字段
     * @param id
     * @param customVos
     */
    private void deleteClassifyCustom(String id, List<SuzakuClassifyCustomVo> customVos) {
        List<SuzakuClassifyCustomDto> classifyCustomByClassifyList = suzakuClassifyCustomService.getClassifyCustomByClassifyId(id);
        List<SuzakuClassifyCustomDto> result = classifyCustomByClassifyList.stream().filter((classifyCustomDto) -> !customVos
                .stream()
                .map(SuzakuClassifyCustomVo::getId)
                .collect(Collectors.toList())
                .contains(classifyCustomDto.getId()))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(result)){
            result.forEach(v -> {suzakuClassifyCustomService.removeById(v.getId());});
        }
    }
}
