package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  0手工调库；1自动调库
 */
public enum InfoSignToolTransferEnums {
	HADEL(0, "手工调库"),
    ACTIVE(1, "自动调库");

    private int code;
    private String desc;

    InfoSignToolTransferEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InfoSignToolTransferEnums enume : InfoSignToolTransferEnums.values()) {
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
