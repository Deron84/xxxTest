package com.rail.bo.enums;

/**
 * @Author: syl
 * @Date: 2019/1/15 0015 11:33
 * @Description:  0 1 2 3 
 */
public enum BrhLevelEnums {
    ZRO(0, "0"),
    ONE(1, "1"),
	TWO(2, "2"),
	TREE(3, "3");

    private int code;
    private String desc;

    BrhLevelEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        for (BrhLevelEnums enume : BrhLevelEnums.values()) {
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
