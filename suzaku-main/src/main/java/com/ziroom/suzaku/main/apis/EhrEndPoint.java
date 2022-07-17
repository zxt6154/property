package com.ziroom.suzaku.main.apis;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.tech.boot.annotation.RetrofitService;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;


@RetrofitService("ehr")
public interface EhrEndPoint {


    /**
     * 根据用户编号查询详情
     *
     * @param userCode 用户编号
     * @return 结果
     */
    @GET("/api/ehr/getUserDetail.action")
    Call<JSONObject> getUserDetail(@Query("userCode") String userCode);

    @GET("/api/ehr/getEmpList.action")
    Call<JSONObject> getEmpList(@QueryMap Map<String, Object> empListReqMap);

    /**
     * 根据部门CODE获取部门信息参数列表
     * @param code
     * @param setId
     * @return
     */
    @GET("/api/ehr/getOrgByCode.action")
    Call<JSONObject> getOrgByCode(@Query("code") String code, @Query("setId") String setId);

    @GET("/api/ehr/getChildOrgs.action")
    Call<JSONObject> getChildOrgs(@Query("parentId") String parentId, @Query("setId") String setId);

    @GET("/api/ehr/getOrgList")
    Call<JSONObject> getOrgList(@Query("city") String city, @Query("orgLevel") int orgLevel, @Query("page") int page,
                                @Query("size") int size);

    @GET("/api/ehr/getOrgList")
    Call<JSONObject> getOrgList(@Query("orgLevel") int orgLevel, @Query("page") int page, @Query("size") int size);


    @GET("/api/ehr/getEhrDept.action?level=1&flag=1")
    Call<JSONObject> getSubordinates(@Query("userCode") String userCode);
}
