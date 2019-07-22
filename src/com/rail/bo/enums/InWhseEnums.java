package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description: 出入网标识1入网；2出网
 */
public enum InWhseEnums {
	   IN(1, "在库"),
		OUT(2, "出库");

    private int code;
    private String desc;

    InWhseEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InWhseEnums enume : InWhseEnums.values()) {
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
