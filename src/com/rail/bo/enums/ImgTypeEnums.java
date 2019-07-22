package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  0图片；1视频
 */
public enum ImgTypeEnums {
    PIG(0, "图片"),
	VIEDIO(1, "视频");

    private int code;
    private String desc;

    ImgTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (ImgTypeEnums enume : ImgTypeEnums.values()) {
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
