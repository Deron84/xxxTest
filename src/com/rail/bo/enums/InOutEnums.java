package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description: 1入网；2出网 1入库；2出库
 */
public enum InOutEnums {
    IN(1, "出库"),
    OUT(2, "入库");

    private int code;
    private String desc;

    InOutEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InOutEnums enume : InOutEnums.values()) {
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
