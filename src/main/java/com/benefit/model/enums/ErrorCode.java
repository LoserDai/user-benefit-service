package com.benefit.model.enums;

/**
 * 错误码
 */
public enum ErrorCode {


    PARAMS_ERROR(40000, "请求参数错误", "Request parameter error"),
    NULL_ERROR(40001, "请求数据为空", "Request data is empty"),
    PAGE_ERROR(40002,"页码不合法","PageNum is not ok"),
    NOT_LOGIN(40100, "未登录", "Not logged in"),
    NO_AUTH(40101, "无权限", "No permission"),
    FORBIDDEN(40301, "禁止操作", "Prohibited operation"),
    IS_REGISTER(40002,"该用户已注册","This user has been registered"),
    SYSTEM_ERROR(50000, "系统内部异常", "System internal exception");



    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
