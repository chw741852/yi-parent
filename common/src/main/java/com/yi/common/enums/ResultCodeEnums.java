package com.yi.common.enums;

/**
 * 结果代码枚举 <br/>
 * Integer code 错误码 <br/>
 * String value 错误信息 <br/>
 * 0.成功；101.缺少必要参数；102.系统内部异常；103.其他错误；
 */
public enum ResultCodeEnums {

    /** 成功 */
    SUCCESS(0, "成功"),
    /** 其它错误 */
    C_101(101, "缺少必要参数"),
    /** 缺少必要参数 */
    C_102(102, "系统内部异常"),
    /** 参数无效 */
    C_103(103, "其他错误");


    /** 错误码 */
    private Integer code;
    /** 错误信息 */
    private String value;

    private ResultCodeEnums(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
