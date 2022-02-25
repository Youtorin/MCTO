package com.yangdonglin.mcto.module;

public enum AjaxResponseErrorCode {
    /**
     * 错误类型
     */
    ERROR(1, "错误"),

    ERROR_TENANT(2, "请选择需要登录的租户"),

    ERROR_PARAM(3, "参数错误");

    AjaxResponseErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 编号
     */
    private final Integer code;
    /**
     * 错误信息
     */
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
