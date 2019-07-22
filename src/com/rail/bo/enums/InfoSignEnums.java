package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  0未审核;1审核通过；2审核不通过
 */
public enum InfoSignEnums {
    NORMAL(0, "未审核"),
    YSE(1, "审核通过"),
	NO(2, "审核不通过"),
	COMPLETE(3, "维修完成");

    private int code;
    private String desc;

    InfoSignEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InfoSignEnums enume : InfoSignEnums.values()) {
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
