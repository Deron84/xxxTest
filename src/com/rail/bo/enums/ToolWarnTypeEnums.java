package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  1报废预警 2 检修预警
 */
public enum ToolWarnTypeEnums {
    FEI(1, "报废预警"),
    REPAIRE(2, "检修预警");

    private int code;
    private String desc;

    ToolWarnTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ToolWarnTypeEnums enume : ToolWarnTypeEnums.values()) {
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
