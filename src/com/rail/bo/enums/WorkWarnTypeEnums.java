package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  1工单过期人未全出、2工单结束工具未全出
 */
public enum WorkWarnTypeEnums {
    PEAPLE(0, "工单过期人未全出"),
    TOOS(1, "工单结束工具未全出"),
    SKY(3, "天窗预警,工单到期未结束"),
	SKYBEFORE(4, "天窗预警,工单到时未执行"),
	SKYINNET(5, "天窗预警,工单到时未入网"),
	SKYOUTNET(6, "天窗预警,工单到时未出网");
	

    private int code;
    private String desc;

    WorkWarnTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (WorkWarnTypeEnums enume : WorkWarnTypeEnums.values()) {
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
