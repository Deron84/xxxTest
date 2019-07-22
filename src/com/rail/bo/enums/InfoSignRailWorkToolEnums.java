package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description: 0未出库、1已出库、2已入网、3已出网、4已入库
 */
public enum InfoSignRailWorkToolEnums {
    ANDO(0, "未出库"),
    OUTWHERE(1, "已出库"),
    INACESS(2, "已入网"),
    OUTACESS(3, "已出网"),
	INWHES(4, "已入库");

    private int code;
    private String desc;

    InfoSignRailWorkToolEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InfoSignRailWorkToolEnums enume : InfoSignRailWorkToolEnums.values()) {
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
