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
@AllArgsConstructor
@Getter//managtypeTrans
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Api(value = "管理类型")
public enum ManagerType {

    ALL(0, "全部"),

    IT(1, "IT资产"),

    ADMINISTRATION(2, "门店"),

    WORKSTATION(3, "工作站"),

    OUTLET(4, "职能行政"),
    ZIROOM_FLAT(5, "自如寓");

    //状态码
    private Integer value;

    //状态描述
    private String label;


    private static Map<Integer, ManagerType> map = Arrays.stream(ManagerType.values()).collect(Collectors.toMap(ManagerType::getValue, Function.identity()));

    public static ManagerType getByCode(Integer code) {
        return map.get(code);
    }


    private static Map<String, ManagerType> maps = Arrays.stream(ManagerType.values()).collect(Collectors.toMap(ManagerType::getLabel, Function.identity()));
    public static ManagerType getByLabel(String label) {
        return maps.get(label);
    }
    /**
     * 获取所有的任务类型
     * @return List<ImportState>
     */
    public static List<ManagerType> getAllManagerTypes() {
        return Arrays.stream(ManagerType.values()).collect(Collectors.toList());
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
        for(ManagerType taskType : ManagerType.values()) {
            if(Objects.equals(taskType.value, code)) {
                return true;
            }
        }
        return false;
    }
}
