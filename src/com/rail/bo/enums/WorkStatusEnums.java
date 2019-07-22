package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:0未开始、1进行中、2已结束、3预警
 */
public enum WorkStatusEnums {
    UNDO(0, "未开始"),
    ACTION(1, "进行中"),
	END(2, "已结束"),
	WARING(3, "预警");

    private int code;
    private String desc;

    WorkStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (WorkStatusEnums enume : WorkStatusEnums.values()) {
            if (enume.getCode() == code) {
                return enume.getDesc();
            }
        }

        return "未知枚举";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
