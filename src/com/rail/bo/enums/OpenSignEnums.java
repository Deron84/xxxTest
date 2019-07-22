package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description: 0能开门；1不能开门
 */
public enum OpenSignEnums {
    YES(0, "能开门"),
    NO(1, "不能开门");

    private int code;
    private String desc;

    OpenSignEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (OpenSignEnums enume : OpenSignEnums.values()) {
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
