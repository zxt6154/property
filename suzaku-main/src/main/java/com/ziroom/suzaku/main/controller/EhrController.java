package com.ziroom.suzaku.main.controller;

import com.alibaba.fastjson.JSON;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import com.ziroom.suzaku.main.components.EhrComponent;
import com.ziroom.suzaku.main.config.UserCacheService;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.SuzakuUserInfoModel;
import com.ziroom.suzaku.main.model.dto.req.ehrs.EhrNextOrgReq;
import com.ziroom.suzaku.main.model.dto.req.ehrs.EhrOrgReq;
import com.ziroom.suzaku.main.model.dto.req.ehrs.UserDetailReq;
import com.ziroom.suzaku.main.model.dto.resp.EhrDeptResp;
import com.ziroom.suzaku.main.model.dto.resp.UserDetailResp;
import com.ziroom.suzaku.main.utils.StringUtils;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/api/v1/ehr")
@Api(tags = "查询ehr接口")
public class EhrController {

    @Resource
    private EhrComponent ehrComponent;

    @Autowired
    private EhrService ehrService;

    @Autowired
    private UserCacheService userCacheService;


    @GetMapping("deptTreepathName")
    @ApiOperation(value = "获取当前登录人的主职部门树")
    public JsonResult deptTreepathName(@RequestParam String uidStr) {
        log.info("EhrController.getCurrentDept");
        return JsonResult.ok(ehrComponent.deptTreepathName(uidStr));
    }

    @GetMapping("centerDept")
    @ApiOperation(value = "获取当前登录人的主职部门树")
    public JsonResult centerDept(@RequestParam String uidStr) {
        log.info("EhrController.getCurrentDept");
        String strUid = "[" + uidStr +",]";
        //获取中心部门
        String center = ehrComponent.getEhrUserDetail(strUid).get(0).getCenter();
        return JsonResult.ok(center);
    }

    /**
     * 查询所有用户信息
     */
    @GetMapping("userInfoList")
    public JsonResult<List<SuzakuUserInfoModel>> getAllUserInfoList() {
        return JsonResult.ok(userCacheService.getAllUserInfoList());
    }

    /**
     * 查询中心部门信息
     */
    @GetMapping("getCenterDepartmentInfo")
    public JsonResult<String> getCenterDepartmentInfo(@RequestParam String email) {
        if(StringUtils.isNotBlank(email)){
            String[] split = email.split("@");
            String[] split1 = split[0].split("\\(");
            email = split1[1];
            String userInfo = ehrService.getUserInfo(email);
            return JsonResult.ok(userInfo);
        }
        return JsonResult.ok("");
    }

    @PostMapping("getOrgByCode")
    @ApiOperation(value = "根据部门编码获取部门信息", httpMethod = "POST")
    public JsonResult<EhrDeptResp> getOrgByCode(@RequestBody EhrOrgReq ehrOrgReq) {
        ehrOrgReq.validate();
        log.info("EhrController.getOrgByCode params:{}", JSON.toJSONString(ehrOrgReq));
        ehrOrgReq.setSetId("101");
        return JsonResult.ok(ehrComponent.getOrgByCode(ehrOrgReq.getDeptId(), ehrOrgReq.getSetId()));
    }

    @GetMapping("levelDept")
    @ApiOperation(value = "获取指定级别的部门", httpMethod = "GET")
    public JsonResult<Set<EhrDeptResp>> getLevelDept(@RequestParam Integer level) {
        log.info("EhrController.getLevelDept params:{}", level);
        return JsonResult.ok(ehrComponent.getOppositeLevelOrgList(level));
    }

    @PostMapping("thirdDept")
    @ApiOperation(value = "获取所有的三级部门", httpMethod = "POST")
    public JsonResult<Set<EhrDeptResp>> getAllThirdDepartment() {
        log.info("EhrController.getAllThirdDepartment");
        return JsonResult.ok(ehrComponent.getOppositeLevelOrgList(3));
    }

    @PostMapping("currentDept")
    @ApiOperation(value = "获取当前登录人的三级部门")
    public JsonResult<EhrDeptResp> getCurrentDept() {
        log.info("EhrController.getCurrentDept");
        return JsonResult.ok(ehrComponent.getCurrentThirdDept());
    }

    @PostMapping("principalDeptTreeStr")
    @ApiOperation(value = "获取当前登录人的主职部门树")
    public JsonResult<String> getPrincipalDeptTree(@RequestParam String uidStr) {
        log.info("EhrController.getCurrentDept");
        return JsonResult.ok(ehrComponent.getPrincipalDept(uidStr));
    }

    @PostMapping("principalDeptTree")
    @ApiOperation(value = "获取当前登录人的主职部门树")
    public JsonResult<String> getPrincipalDeptTree() {
        log.info("EhrController.getCurrentDept");
        return JsonResult.ok(ehrComponent.getPrincipalDept(null));
    }

    @PostMapping("getSubordinates")
    @ApiOperation(value = "获取指定员工的上下级")
    public JsonResult<List<UserDetailResp>> getSubordinates(@RequestBody UserDetailReq userDetailReq) {
        log.info("EhrController.getSubordinates params:{}", userDetailReq.getUserCode());
        return JsonResult.ok(ehrComponent.getSubordinates(userDetailReq.getUserCode()));
    }

    @PostMapping("nextOrg")
    @ApiOperation(value = "根据部门编码，查询子部门列表", notes = "目前公司编码默认为101", httpMethod = "POST")
    public JsonResult<Set<EhrDeptResp>> getNextOrgEmpList(@RequestBody EhrNextOrgReq ehrNextOrgReq) {
        ehrNextOrgReq.validate();
        log.info("EhrController.getNextOrg params:{}", JSON.toJSONString(ehrNextOrgReq));
        ehrNextOrgReq.setSetId("101");
        return JsonResult.ok(ehrComponent.getChildOrgs(ehrNextOrgReq.getDeptId(), ehrNextOrgReq.getSetId()));
    }

    @GetMapping("getOrgTree")
    @ApiOperation(value = "根据组织系统号查询部门组织树", notes = "用于前端下拉菜单树形展示", httpMethod = "GET")
    public JsonResult getOrgTree(@RequestParam("deptCode") String deptCode) {
        log.info("EhrController.getOrgList params:{}", deptCode);
        return JsonResult.ok(ehrComponent.getDeptTree(deptCode));
    }

    @GetMapping("getOrgTree2")
    @ApiOperation(value = "根据组织系统号查询部门组织树, 只到3级", notes = "用于前端下拉菜单树形展示", httpMethod = "GET")
    public JsonResult getOrgTree2(@RequestParam("deptCode") String deptCode) {
        log.info("EhrController.getOrgList params:{}", deptCode);
        return JsonResult.ok(ehrComponent.getDeptTree2(deptCode));
    }


    @GetMapping("getThridDepts")
    @ApiOperation(value = "获取当前层级的下层级或下下层级所有部门", notes = "获取当前层级的下层级或下下层级所有部门", httpMethod = "GET")
    public JsonResult getThridDepts(@RequestParam("deptCode") String deptCode, @RequestParam("loopTime") String loopTime) {
        log.info("EhrController.getOrgList params:{}", deptCode, loopTime);

        return JsonResult.ok(ehrComponent.getThridDepts(deptCode, loopTime));
    }

}
