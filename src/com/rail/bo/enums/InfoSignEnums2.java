package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  0未确认;1已确认
 */
public enum InfoSignEnums2 {
    NO(0, "未确认"),
    YSE(1, "已确认");

    private int code;
    private String desc;

    InfoSignEnums2(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (InfoSignEnums2 enume : InfoSignEnums2.values()) {
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
