package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  1意外打开、2长时间未关闭、3门禁检测异常、4断电故障
 */
public enum AccessWarnTypeEnums {
    OEPN(1, "意外打开"),
    LONG_NO_CLOSE(2, "长时间未关闭"),
    CHECK(3, "门禁检测异常"),
    OUT_LIGNTT(4, "断电故障");

    private int code;
    private String desc;

    AccessWarnTypeEnums(int code, String desc) {
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
