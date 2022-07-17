package com.ziroom.suzaku.main.enums;

/**
 * 资产状态
 * @author xuzeyu
 */
public enum AssertStatusEnum {

    ASSERT_LEAVE(1, "闲置"),
    ASSERT_NORECEIVE(2, "待签收"),
    ASSERT_RECEIVE(3, "领用"),
    ASSERT_BORROW(4, "借用"),
    ASSERT_RETURN(5, "退库中"),
    ASSERT_DEAL(6, "处置中"),
    ASSERT_TRANSFER(7, "转移中"),
    ASSERT_SCRAP(8, "报废"),
    ASSERT_LOSE(9, "遗失"),
    ASSERT_SELL(10, "变卖"),
    ;
    private final Integer type;
    private final String desc;

    AssertStatusEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


}
