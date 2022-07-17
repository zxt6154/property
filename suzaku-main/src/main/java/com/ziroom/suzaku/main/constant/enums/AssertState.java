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
 * @zhangxt3
 */
@Api(value = "资产状态")
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AssertState {

    State_1(1, "闲置"),

    State_2(2, "待签收"),

    State_3(3, "领用"),

    State_4(4, "借用"),

    State_5(5, "退库中"),

    State_6(6, "异常中"),

    State_7(7, "转移中"),

    State_8(8, "报废"),

    State_9(9, "遗失"),

    State_10(10, "变卖");


    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, AssertState> map = Arrays.stream(AssertState.values()).collect(Collectors.toMap(AssertState::getValue, Function.identity()));

    private static Map<String, AssertState> maps = Arrays.stream(AssertState.values()).collect(Collectors.toMap(AssertState::getLabel, Function.identity()));

    public static AssertState getByCode(Integer code) {
        return map.get(code);
    }

    public static AssertState getByLabel(String label) {  return maps.get(label);  }

    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<AssertState> getAllTaskType() {
        return Arrays.stream(AssertState.values()).collect(Collectors.toList());
    }

    /**
     * 判断传入的状态码是不是合法的，当没有找到对应的code或者传入的code为null时，返回false
     * @param code 状态码
     * @return 是否合法 合法:true 不合法:false
     */
    public static boolean isValid(Integer code){
        if(Objects.isNull(code)) {
            return false;
        }
        for(AssertState taskType : AssertState.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }
}
