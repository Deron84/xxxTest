package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  1 工具 2 人员  3 工单 4 预警 5门禁预警
 */
public enum ApiSendMsgFlagEnums {
    TOOL(1, "工具"),
    EMPLOYEE(2, "人员"),
	WORK_ORDER(3, "工单"),
	WARN(4, "预警"),
	ACCESS_WARN(5, "门禁预警");

    private int code;
    private String desc;

    ApiSendMsgFlagEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ApiSendMsgFlagEnums enume : ApiSendMsgFlagEnums.values()) {
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
