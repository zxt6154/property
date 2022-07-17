package com.ziroom.suzaku.main.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @zhangxt
 */
@Api(value = "入库方式")
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ImportWay {

    ALL(0, "全部"),

    PURCHASR(1, "采购"),

    RENDER(2, "租赁"),

    CHECKING(3, "盘点"),

    ALLOT(4, "调拨"),

    OTHERS(5, "其它");

    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, ImportWay> map = Arrays.stream(ImportWay.values()).collect(Collectors.toMap(ImportWay::getValue, Function.identity()));

    public static ImportWay getByCode(Integer code) {
        return map.get(code);
    }

    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<ImportWay> getAllImportWays() {
        return Arrays.stream(ImportWay.values()).collect(Collectors.toList());
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
        for(ImportWay taskType : ImportWay.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }
}
