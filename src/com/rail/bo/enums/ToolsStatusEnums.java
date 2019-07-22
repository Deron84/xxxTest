package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  0正常；1维护；3报废；4停用
 */
public enum ToolsStatusEnums {
    NORMAL(0, "正常"),
    REPAIE(1, "维护"),
    DEL(3, "报废"),
    STOP(4, "停用");

    private int code;
    private String desc;

    ToolsStatusEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ToolsStatusEnums enume : ToolsStatusEnums.values()) {
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
