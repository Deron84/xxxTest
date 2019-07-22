package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description: 0未入网，1已入网、2已出网
 */
public enum InfoSignRailWorkEmployeeEnums {
    ANDO(0, "未入网"),
    INACESS(1, "已入网"),
    OUTACESS(2, "已出网");

    private int code;
    private String desc;

    InfoSignRailWorkEmployeeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InfoSignRailWorkEmployeeEnums enume : InfoSignRailWorkEmployeeEnums.values()) {
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
