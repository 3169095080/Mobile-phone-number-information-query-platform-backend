package org.example.phonelocatebackend.model.enums;

/**
 * 错误码字典
 */
public enum ErrorCode {

    /**
     * 成功状态码
     */
    SUCCESS(0, "ok"),
    /**
     * 手机号格式错误状态码
     */
    PHONE_NUM_ERROR(40000, "请输入正确形式的手机号"),
    /**
     * 未知运营商状态码
     */
    UNKNOWN_OPERATOR(40001, "未知运营商"),
    /**
     * 未知归属地状态码
     */
    LOCATION_NOT_FOUND(40002, "未知归属地"),
    /**
     * 手机号非数字状态码
     */
    PHONE_NUMBER_NOT_NUMERIC(40003, "手机号只能包含数字"),
    /**
     * 手机号为空状态码
     */
    PHONE_NUMBER_REQUIRED(40004, "手机号不能为空");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code    状态码
     * @param message 信息
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过状态码获取对应的ErrorCode对象
     *
     * @param code 状态码
     * @return 对应的ErrorCode对象，如果没有匹配的错误码，则返回 null 或者其他默认的错误码
     */
    public static ErrorCode getByCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取信息
     *
     * @return 信息
     */
    public String getMessage() {
        return message;
    }
}