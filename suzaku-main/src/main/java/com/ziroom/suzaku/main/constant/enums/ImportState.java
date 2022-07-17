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
@Api(value = "入库方式")
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum ImportState {

    ALL(0, "全部"),

    WAITING_CHECKING(1, "待审核"),

    PASS(2, "审核通过"),

    REJECT(3, "审核驳回");

    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, ImportState> map = Arrays.stream(ImportState.values()).collect(Collectors.toMap(ImportState::getValue, Function.identity()));

    public static ImportState getByCode(Integer code) {
        return map.get(code);
    }

    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<ImportState> getAllImportStates() {
        return Arrays.stream(ImportState.values()).collect(Collectors.toList());
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
        for(ImportState taskType : ImportState.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }
}
