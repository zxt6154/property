package com.ziroom.suzaku.main.components;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ziroom.suzaku.main.apis.EhrEndPoint;
import com.ziroom.suzaku.main.common.RetrofitCallAdaptor;
import com.ziroom.suzaku.main.config.OperatorContext;
import com.ziroom.suzaku.main.model.dto.req.EhrEmpListReq;
import com.ziroom.suzaku.main.model.dto.req.ehrs.EhrOrgListReq;
import com.ziroom.suzaku.main.model.dto.resp.*;
import com.ziroom.suzaku.main.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import retrofit2.Call;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EhrComponent {
    /**
     * ehr响应中的错误信息属性名
     */
    private final static String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    private final static String MESSAGE_ATTRIBUTE = "message";
    /**
     * ehr响应中的错误码属性名
     */
    private final static String ERROR_CODE_ATTRIBUTE = "errorCode";

    private final static String CODE_ATTRIBUTE = "code";
    /**
     * ehr响应中的数据属性名
     */
    private final static String DATA_ATTRIBUTE = "data";
    /**
     * ehr响应中的状态属性名
     */
    private final static String STATUS_ATTRIBUTE = "status";
    /**
     * 默认公司编码
     */
    private final static String DEFAULT_SETID = "101";


    @Autowired
    private EhrEndPoint ehrEndPoint;


    /**
     * 模糊查询用户
     *
     * @param ehrEmpListReq req
     * @return 结果
     */
    public List<UserResp> getEmpList(EhrEmpListReq ehrEmpListReq) {
        List<UserResp> resp = Lists.newArrayList();
        Map<String, Object> empReqMap = initReqMap(ehrEmpListReq);
        Call<JSONObject> response = ehrEndPoint.getEmpList(empReqMap);

        Optional.ofNullable(RetrofitCallAdaptor.execute(response)).ifPresent(respData -> {
            if (Objects.equals(respData.getString(ERROR_CODE_ATTRIBUTE), "20000")) {
                JSONArray data = respData.getJSONArray(DATA_ATTRIBUTE);
                data.stream().map(o -> JSONObject.parseObject(JSON.toJSONString(o))).forEach(jsonObject -> {
                    UserResp ehrUserResp = new UserResp();
                    ehrUserResp.setName(jsonObject.getString("fullName"));
                    ehrUserResp.setCode(jsonObject.getString("empCode"));
                    ehrUserResp.setEmail(jsonObject.getString("email"));
                    resp.add(ehrUserResp);
                });
            }
        });
        return resp;
    }
    /**
     * 根据用户系统号获取用户详情
     * userCodes最多传10个 示例：["20237106", “20118321”]
     *
     * @param uidString 用户系统号列表
     * @return List<User>
     */
    @RecordLogger
    public List<EhrUserDetailResp> getEhrUserDetail(String uidString) {
        log.info("EhrService.getEhrUserDetail params:{}", uidString);

        Call<JSONObject> call = ehrEndPoint.getUserDetail(uidString);
        JSONObject response = RetrofitCallAdaptor.execute(call);
        String failure = "failure";
        if (failure.equals(response.getString(STATUS_ATTRIBUTE))) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getEhrUserDetail has occurred error message: {}", errorMessage);
            return Lists.newArrayList();
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        List<EhrUserDetailResp> userList = Lists.newArrayList();
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject ehrUser = data.getJSONObject(i);
            EhrUserDetailResp ehrUserDetailResp = new EhrUserDetailResp();
            ehrUserDetailResp.setEmail(ehrUser.getString("email"));
            ehrUserDetailResp.setAvatar(ehrUser.getString("photo"));
            ehrUserDetailResp.setCode(ehrUser.getString("emplid"));
            ehrUserDetailResp.setName(ehrUser.getString("name"));
            ehrUserDetailResp.setGroupCode(ehrUser.getString("groupCodeNew"));
            ehrUserDetailResp.setGroupName(ehrUser.getString("group"));
            ehrUserDetailResp.setTreePath(ehrUser.getString("treePath"));
            ehrUserDetailResp.setJobIndicator(ehrUser.getString("jobIndicator"));
            ehrUserDetailResp.setLevelName(ehrUser.getString("levelName"));
            ehrUserDetailResp.setDeptName(ehrUser.getString("dept"));
            ehrUserDetailResp.setDeptCode(ehrUser.getString("deptCodeNew"));
            ehrUserDetailResp.setCenter(ehrUser.getString("center"));
            ehrUserDetailResp.setCenterId(ehrUser.getString("centerCode"));
            ehrUserDetailResp.setDesc(ehrUser.getString("descr"));
            ehrUserDetailResp.setSetId(ehrUser.getString("setId"));
            ehrUserDetailResp.setJobCodeNew(ehrUser.getString("jobCodeNew"));
            userList.add(ehrUserDetailResp);
        }
        log.info("EhrService.getEhrUserDetail request success result:{}", userList);
        return userList;
    }

    public String getPrincipalDept(String uidStr) {

        List<EhrUserDetailResp> ehrUserDetail = null;
         if(StringUtils.isNotBlank(uidStr)){
             ehrUserDetail = getEhrUserDetail(uidStr); //OperatorContext.getOperator()
          } else {
             ehrUserDetail = getEhrUserDetail("[" + OperatorContext.getOperator() + "]");
         }

        if (!CollectionUtils.isEmpty(ehrUserDetail)) {
            for (EhrUserDetailResp ehrUser : ehrUserDetail) {
                if (ehrUser.getJobIndicator().equals("P")) {
                    // 只截取 平台-中心-部门，其他接口请勿使用
                    String treePath = ehrUser.getTreePath();
                    List<String> treeList = Arrays.asList(treePath.split(","));
                    List<String> rtv = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(treeList)) {
                        if (treeList.size() == 3 || treeList.size() == 4 || treeList.size() == 5) {
                            rtv = treeList.subList(2, treeList.size());
                        } else if (treeList.size() == 6) {
                            rtv = treeList.subList(2, 5);
                        }
                        rtv = treeList.subList(2, treeList.size());
                        return String.join("/", rtv);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取该员工的上下级信息
     * @param userId 需要查询的员工
     * @return List<UserDetailResp>
     */
    public List<UserDetailResp> getSubordinates(String userId) {
        log.info("EhrService.getSubordinates params:{}", userId);
        Call<JSONObject> call = ehrEndPoint.getSubordinates(userId);
        JSONObject response = RetrofitCallAdaptor.execute(call);
        String failure = "failure";
        if (failure.equals(response.getString(STATUS_ATTRIBUTE))) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getSubordinates has occurred error message: {}", errorMessage);
            return Lists.newArrayList();
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        List<UserDetailResp> userList = Lists.newArrayList();
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject ehrUser = data.getJSONObject(i);
            UserDetailResp UserDetailResp = new UserDetailResp();
            UserDetailResp.setEmail(ehrUser.getString("email"));
            UserDetailResp.setUserName(ehrUser.getString("userName"));
            UserDetailResp.setUserCode(ehrUser.getString("userCode"));
            userList.add(UserDetailResp);
        }
        return userList;
    }

    public EhrDeptResp getCurrentThirdDept() {
        List<EhrUserDetailResp> ehrUserDetail = getEhrUserDetail("[" + OperatorContext.getOperator() + "]");
        if (!CollectionUtils.isEmpty(ehrUserDetail)) {
            for (EhrUserDetailResp ehrUser : ehrUserDetail) {
                if (ehrUser.getJobIndicator().equals("P")) {
                    // 主职
                    String treePath = ehrUser.getTreePath();
                    String[] treePaths = treePath.split(",");
                    EhrDeptResp resp = new EhrDeptResp();
                    if (treePaths.length >= 5) {
                        String thirdDept = treePaths[4];
                        resp.setCode(thirdDept);
                    }
                    return resp;
                }
            }
        }
        return new EhrDeptResp();
    }

    public String deptTreepathName(String uidStr){

        List<EhrUserDetailResp> ehrUserDetail = getEhrUserDetail(uidStr); //OperatorContext.getOperator()
        if (!CollectionUtils.isEmpty(ehrUserDetail)) {
            for (EhrUserDetailResp ehrUser : ehrUserDetail) {
                if (ehrUser.getJobIndicator().equals("P")) {
                    // 只截取 平台-中心-部门，其他接口请勿使用
                    String treePath = ehrUser.getTreePath();
                    List<String> treeList = Arrays.asList(treePath.split(","));

                    List<Serializable> descrShort = treeList.stream().map(tree -> {
                        Call<JSONObject> call = ehrEndPoint.getOrgByCode(tree, ehrUser.getSetId());
                        JSONObject response = RetrofitCallAdaptor.execute(call);
                        String failure = "failure";
                        if (failure.equals(response.getString(STATUS_ATTRIBUTE))) {
                            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
                            log.error("EhrService.getEhrUserDetail has occurred error message: {}", errorMessage);
                            return Lists.newArrayList();
                        }
                        JSONObject data = response.getJSONObject(DATA_ATTRIBUTE);

                        return data.getString("descrShort");
                    }).collect(Collectors.toList());

                    String replace = descrShort.toString().trim().replace(",", "/").replace("[", "").replace("]", "");
                    return replace;
                }
                //
            }
        }
        return null;
    }

    private Map<String, Object> initReqMap(Object o) {
        Map<String, Object> result = Maps.newHashMap();
        FieldUtils.getAllFieldsList(o.getClass()).forEach(field -> {
            try {
                if (Objects.nonNull(FieldUtils.readDeclaredField(o, field.getName(), true))) {
                    result.put(field.getName(), FieldUtils.readDeclaredField(o, field.getName(), true));
                }
            } catch (IllegalAccessException e) {
                // ignore
            }
        });
        return result;
    }

    private String addQuotationMark(String uid) {
        return "\"" + uid + "\"";
    }

    /**
     * 根据条件查询部门列表，目前仅用来查询部门树的根节点
     *
     * @return Set<EhrDeptResp>
     */
    public Set<EhrDeptResp> getOrgList(EhrOrgListReq ehrOrgListReq) {
        log.info("EhrComponent.getOrgList params:{}", JSON.toJSONString(ehrOrgListReq));
        Call<JSONObject> call = ehrEndPoint.getOrgList(ehrOrgListReq.getOrgLevel(), ehrOrgListReq.getPage(), ehrOrgListReq.getSize());
        JSONObject response = RetrofitCallAdaptor.execute(call);
        String success = "20000";
        if (!success.equals(response.getString(ERROR_CODE_ATTRIBUTE))) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getOrgList has occurred error message: {}", errorMessage);
            return Sets.newHashSet();
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        Set<EhrDeptResp> deptSet = new HashSet<>();
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject dept = data.getJSONObject(i);
            EhrDeptResp ehrDeptResp = new EhrDeptResp();
            // 这里取部门新编码
            ehrDeptResp.setCode(dept.getString("orgCode"));
            ehrDeptResp.setName(dept.getString("orgName"));
            ehrDeptResp.setTreepath(dept.getString("orgTree"));
            deptSet.add(ehrDeptResp);
        }
//        log.info("EhrService.getOrgList request success result:{}", deptSet);
        return deptSet;
    }
    //@Cached(expire = 3600, cacheType = CacheType.BOTH)
    public Set<EhrDeptResp> getOppositeLevelOrgList(int level) {
        log.info("EhrComponent.getOppositeLevelOrgList params: {}", level);
        int size = 100;
        Call<JSONObject> call = ehrEndPoint.getOrgList(level, 1, size);
        JSONObject response = RetrofitCallAdaptor.execute(call);
        Set<EhrDeptResp> result = Sets.newHashSet();
        String success = "20000";
        if (!success.equals(response.getString(ERROR_CODE_ATTRIBUTE))) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrComponent.getAllThirdOrgList has occurred error message: {}", errorMessage);
            return result;
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject dept = data.getJSONObject(i);
            EhrDeptResp ehrDeptResp = new EhrDeptResp();
            // 这里取部门新编码
            ehrDeptResp.setCode(dept.getString("orgCode"));
            ehrDeptResp.setName(dept.getString("orgName"));
            ehrDeptResp.setTreepath(dept.getString("orgTree"));
            result.add(ehrDeptResp);
        }
        int totleCount = response.getInteger("totleCount") - size;
        int loopCount = (int) Math.ceil((double) totleCount / size);
        List<CompletableFuture<Void>> futureList = new ArrayList<>(loopCount);
        for (int page = 1; page <= loopCount; page++) {
            // 生成一个CompletableFuture来访问ehr接口
            int finalPage = page;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                EhrOrgListReq req = new EhrOrgListReq();
                req.setOrgLevel(level);
                req.setPage(finalPage + 1);
                req.setSize(size);
                result.addAll(getOrgList(req));
            }).exceptionally(throwable -> null);
            futureList.add(future);
        }
        // 通过allOf方法实现异步执行后，同步搜集结果，这里没有超时控制
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        allFuture.thenApply(e -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
        log.info("EhrService.getAllThirdOrgList request success result:{}", result);
        return result;
    }

    //根据部门系统号获取部门组织树
    //@Cached(expire = 1, timeUnit = TimeUnit.DAYS, cacheType = CacheType.BOTH)
    public EhrDeptTreeResp getDeptTree(String deptCode){
        //先获取根节点
        EhrDeptResp rootDept = getOrgByCode(deptCode, "101");
        if (Objects.isNull(rootDept)){
            return null;
        }
        EhrDeptTreeResp deptTree = new EhrDeptTreeResp();
        deptTree.setLabel(rootDept.getName());
        deptTree.setValue(rootDept.getCode());
        getChildDepts(deptTree);
        return deptTree;
    }

    public void getChildDepts(EhrDeptTreeResp deptTree){
        String parentDeptCode = deptTree.getValue();
        Set<EhrDeptResp> childOrgs = getChildOrgs(parentDeptCode, "101");
        if (Objects.isNull(childOrgs) || childOrgs.size() == 0){
            deptTree.setChildren(null);
            deptTree.setLeaf(true);
            return;
        }
        for (EhrDeptResp childOrg : childOrgs) {
            EhrDeptTreeResp childDeptTree = new EhrDeptTreeResp();
            childDeptTree.setLabel(childOrg.getName());
            childDeptTree.setValue(childOrg.getCode());
            getChildDepts(childDeptTree);
            deptTree.getChildren().add(childDeptTree);
        }
    }

    /**
     * 通过父部门id和公司编码获取子部门
     *
     * @param parentId 父部门id
     * @param setId    公司编码
     * @return Set<EhrDeptResp>
     */
    public Set<EhrDeptResp> getChildOrgs(String parentId, String setId) {
        log.info("EhrService.getChildOrgs params:{} {}", parentId, setId);
        Call<JSONObject> call = ehrEndPoint.getChildOrgs(parentId, setId);
        JSONObject response = RetrofitCallAdaptor.execute(call);
        Set<EhrDeptResp> deptSet = new HashSet<>();
        if (response.getInteger(ERROR_CODE_ATTRIBUTE) != 0) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getChildOrgs has occurred error message: {}", errorMessage);
            return deptSet;
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject dept = data.getJSONObject(i);
            EhrDeptResp ehrDeptResp = new EhrDeptResp();
            // 这里取部门新编码
            ehrDeptResp.setCode(dept.getString("newCode"));
            ehrDeptResp.setName(dept.getString("name"));
            deptSet.add(ehrDeptResp);
        }
        log.info("EhrService.getChildOrgs request success result:{}", deptSet);
        return deptSet;
    }

    /**
     * 通过部门编码和公司编码查询指定部门信息
     *
     * @param deptCode 部门编码
     * @param setId    公司编码
     * @return EhrDeptResp
     */
    public EhrDeptResp getOrgByCode(String deptCode, String setId) {
        // 目前公司编码只支持总部Ta
        log.info("EhrService.getOrgByCode params:{} {}", deptCode, setId);
        Call<JSONObject> call = ehrEndPoint.getOrgByCode(deptCode, setId);
        JSONObject response = RetrofitCallAdaptor.execute(call);
        String failure = "failure";
        if (failure.equals(response.getString(STATUS_ATTRIBUTE))) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getOrgByCode has occurred error message: {}", errorMessage);
            return null;
        }
        JSONObject data = response.getJSONObject(DATA_ATTRIBUTE);
        EhrDeptResp ehrDeptResp = new EhrDeptResp();
        ehrDeptResp.setName(data.getString("descrShort"));
        ehrDeptResp.setCode(data.getString("deptid"));
        ehrDeptResp.setTreepath(data.getString("treepath"));
        log.info("EhrService.getOrgByCode request success result:{}", ehrDeptResp);
        return ehrDeptResp;
    }


    //根据部门系统号获取部门组织树
    //@Cached(expire = 1, timeUnit = TimeUnit.DAYS, cacheType = CacheType.BOTH)
    public EhrDeptTreeResp getDeptTree2(String deptCode){
        //先获取根节点
        EhrDeptResp rootDept = getOrgByCode(deptCode, "101");
        if (Objects.isNull(rootDept)){
            return null;
        }
        EhrDeptTreeResp deptTree = new EhrDeptTreeResp();
        deptTree.setLabel(rootDept.getName());
        deptTree.setValue(rootDept.getCode());
        AtomicInteger count = new AtomicInteger(0);
        getChildDepts2(deptTree, count);
        return deptTree;
    }

    /**
     * 通过父部门id和公司编码获取子部门
     *
     * @param parentId 父部门id
     * @param setId    公司编码
     * @return Set<EhrDeptResp>
     */
    public Set<EhrDeptResp> getChildOrgs2(String parentId, String setId, String count) {
        log.info("EhrService.getChildOrgs params:{} {}", parentId, setId);
        Call<JSONObject> call = ehrEndPoint.getChildOrgs(parentId, setId);
        JSONObject response = RetrofitCallAdaptor.execute(call);
        Set<EhrDeptResp> deptSet = new HashSet<>();
        if (response.getInteger(ERROR_CODE_ATTRIBUTE) != 0) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getChildOrgs has occurred error message: {}", errorMessage);
            return deptSet;
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject dept = data.getJSONObject(i);
            EhrDeptResp ehrDeptResp = new EhrDeptResp();
            // 这里取部门新编码
            ehrDeptResp.setCode(dept.getString("newCode"));
            ehrDeptResp.setName(dept.getString("name"));
            ehrDeptResp.setTreepath(String.valueOf(count));
            deptSet.add(ehrDeptResp);
        }
        log.info("EhrService.getChildOrgs request success result:{}", deptSet);
        return deptSet;
    }

    public void getChildDepts2(EhrDeptTreeResp deptTree, AtomicInteger count){
        int i = count.incrementAndGet();
       if(i <= 3){
           String parentDeptCode = deptTree.getValue();
           Set<EhrDeptResp> childOrgs = getChildOrgs2(parentDeptCode, "101", String.valueOf(i));
           if (Objects.isNull(childOrgs) || childOrgs.size() == 0){
               deptTree.setChildren(null);
               deptTree.setLeaf(true);
               return;
           }
           for (EhrDeptResp childOrg : childOrgs) {
               EhrDeptTreeResp childDeptTree = new EhrDeptTreeResp();
               childDeptTree.setLabel(childOrg.getName());
               childDeptTree.setValue(childOrg.getCode());
               getChildDepts2(childDeptTree, new AtomicInteger(i));
               childDeptTree.setTreeDeep(childOrg.getTreepath());
               deptTree.getChildren().add(childDeptTree);
           }
       }
    }

    //用于前端级联选框
    public List<String> getThridDepts(String deptCode, String loopTime){

        EhrDeptResp rootDept = getOrgByCode(deptCode, "101");
        if (Objects.isNull(rootDept)){
            return null;
        }
        ArrayList<String> deptNames = Lists.newArrayList();

        //获取该部门下所有子部门
        Set<EhrDeptResp> childOrgs = getChildOrgs(rootDept.getCode(), "101");

        if(Integer.parseInt(loopTime) == 1){// 循环一次
            List<String> deptNameStrs = childOrgs.stream().map(child -> {
                return child.getName();
            }).collect(Collectors.toList());
            deptNames.addAll(deptNameStrs);
        }

        if(Integer.parseInt(loopTime) == 0){// 循环两次
            //1、2、3、4
            for (EhrDeptResp childOrg : childOrgs) {
                Set<EhrDeptResp> childOrgs2 = getChildOrgs(childOrg.getCode(), "101");
                List<String> deptNameStrs = childOrgs2.stream().map(child -> {
                    return child.getName();
                }).collect(Collectors.toList());
                deptNames.addAll(deptNameStrs);
            }

        }
      return deptNames;
    }

}
