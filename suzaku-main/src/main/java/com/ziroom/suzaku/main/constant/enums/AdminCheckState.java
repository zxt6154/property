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
@Api(value = "管理员盘点状态")
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AdminCheckState {

    CANCEL(0, "已取消"),

    UNTREATED(1, "未处理"),

    SUCCESS(2, "盘点正常"),

    FAIL(3, "盘点异常"),

    INVALID(4, "盘盈无效"),

    VALID(5, "盘盈有效");

    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, AdminCheckState> map = Arrays.stream(AdminCheckState.values()).collect(Collectors.toMap(AdminCheckState::getValue, Function.identity()));

    public static AdminCheckState getByCode(Integer code) {
        return map.get(code);
    }

    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<AdminCheckState> getAllImportStates() {
        return Arrays.stream(AdminCheckState.values()).collect(Collectors.toList());
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
        for(AdminCheckState taskType : AdminCheckState.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }
}
