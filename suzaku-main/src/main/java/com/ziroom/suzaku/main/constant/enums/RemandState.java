package com.ziroom.suzaku.main.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 退库状态
 * @zhangxt
 */
@Api(value = "退库状态")
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RemandState {
    REMAND_PENDING(1, "待审核"),
    REMAND_AUDITED(2, "审核通过"),
    REMAND_FAIL(3, "审核驳回");

    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, RemandState> map = Arrays.stream(RemandState.values()).collect(Collectors.toMap(RemandState::getValue, Function.identity()));

    public static RemandState getByCode(Integer code) {
        return map.get(code);
    }

    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<RemandState> getAllTaskType() {
        return Arrays.stream(RemandState.values()).collect(Collectors.toList());
    }

    /**
     * 判断传入的状态码是不是合法的，当没有找到对应的code或者传入的code为null时，返回false
     * @param code 状态码,
     * @return 是否合法 合法:true 不合法:false
     */
    public static boolean isValid(Integer code){
        if(Objects.isNull(code)) {
            return false;
        }
        for(RemandState taskType : RemandState.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }

}
