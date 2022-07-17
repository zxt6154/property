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
 * @zhangxt
 */
@Api(value = "员工盘点状态")
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StaffCheckState {

    NOTMADE(1, "未盘"),

    NORMAL(2, "正常"),

    ABNORMAL(3, "异常"),

    SURPLUS(4, "盘盈");

    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, StaffCheckState> map = Arrays.stream(StaffCheckState.values()).collect(Collectors.toMap(StaffCheckState::getValue, Function.identity()));

    public static StaffCheckState getByCode(Integer code) {
        return map.get(code);
    }

    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<StaffCheckState> getAllImportStates() {
        return Arrays.stream(StaffCheckState.values()).collect(Collectors.toList());
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
        for(StaffCheckState taskType : StaffCheckState.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }
}
