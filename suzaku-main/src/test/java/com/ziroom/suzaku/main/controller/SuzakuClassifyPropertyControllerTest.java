package com.ziroom.suzaku.main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.suzaku.main.base.BaseTest;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.vo.ClassifyPropertyVo;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyCustomVo;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * <p>
 * 资产分类属性controller junit
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
class SuzakuClassifyPropertyControllerTest extends BaseTest {


    @Autowired
    private MockMvc mvc;

    private static final String prefix = "/api/v1/property";

    @Test
    void getClassifyPropertyTreeNode() throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(prefix + "/getClassifyPropertyTreeNode")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        JsonResult r = JSON.parseObject(result.getResponse().getContentAsString(),JsonResult.class);
        TestCase.assertEquals(r.getCode(),0);
    }

    @Test
    void getClassifyPropertyInfo() throws Exception {
        String classifyId = "1";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(prefix + "/getClassifyPropertyInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("classifyId",classifyId)
        ).andReturn();
        JsonResult r = JSON.parseObject(result.getResponse().getContentAsString(),JsonResult.class);
        TestCase.assertEquals(r.getCode(),0);
    }

    @Test
    void saveClassifyProperty() throws Exception {
        ClassifyPropertyVo vo = new ClassifyPropertyVo();
        vo.setClassifyName("测试分类6-3-4");
        vo.setSeniorId("112");
        vo.setClassifyUnit("台");
        vo.setClassifyDeadline(6);
        List<SuzakuClassifyCustomVo> customVos = new ArrayList<>();
        SuzakuClassifyCustomVo customVo = new SuzakuClassifyCustomVo();
        customVo.setCustomName("WIFI地址");
        customVo.setRequiredFlag(0);
        customVos.add(customVo);
        vo.setCustomVos(customVos);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(prefix + "/saveClassifyProperty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(vo))
                .header("X-ZCLOUD-TOKEN","eyJraWQiOiJ6Y2xvdWQtc3NvLWRlZmF1bHQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ6Y2xvdWQiLCJzdWIiOiJ6Y2xvdWQtc3NvIiwidXNlckluZm8iOnsiam9iTmFtZSI6IkphdmHlt6XnqIvluIgiLCJkZXB0TmFtZSI6IuS4muWKoeW5s-WPsOe7hCIsInVpZCI6IjYwMDMzNDU3IiwiYXZhdGFyVXJsIjoiaHR0cHM6XC9cL2VocnN0YXRpYy56aXJvb20uY29tXC82MDAzMzQ1Ny5qcGciLCJuaWNrTmFtZSI6IuadjuenieWXoyIsInVzZXJUeXBlIjoyLCJ1c2VyTmFtZSI6ImxpYnMxIiwiZW1haWwiOiJsaWJzMUB6aXJvb20uY29tIn0sImF1dGgiOnsic3lzIjoiQ0FTIiwidG9rZW4iOiJOakF3TXpNME5UY3RZV05qYjNWdWRDNXRZVzVoWjJWdFpXNTBMbUZ3YVMweE5rVXlNMFF3UWpJeVJUUXlNa00yT0VVd05EWXdOVFEzUlRZMU9VWTBOeTB4TmpNME5UUXdOalV5T0RVMkxURT0ifSwiZXhwIjoxNjM0Nzk5ODUxLCJpYXQiOjE2MzQwMDYyNjMsInVzZXJuYW1lIjoibGliczEiLCJzdGF0dXMiOjF9.ckKr1B7RXdyodMXWwoYugMShtciffkzFf4QwLEwbHs1A8j7zHs2YiUM9OmT2Sdsfvj3eC8OJ5N4A5pVocCp-X5C0HWN4tAPfc8s6XJMIXLLdty99JhfyP_TOOq6og5kpRiUpiUhz9ASSoO7Z8yCCWhX0WuQFc3v1cwGX4jrkwupReVSsO3q8ONUpWFS5voeoirlD1wWQk6805F07eCO9ESwrIl30CFwRidQ9an-0C80AoR38T1rrs4HC_QavQ9yrmtbh-NyGivhoJB-e2ZemXM8tcRgHvYR65guKK9GjAY2erQyI3wCKGvgcMKaubToIxpMBRowwopEBo3TxVy7l9w"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        JsonResult r = JSON.parseObject(result.getResponse().getContentAsString(),JsonResult.class);
        TestCase.assertEquals(r.getCode(),0);
    }
}