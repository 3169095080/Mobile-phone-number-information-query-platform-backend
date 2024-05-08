package org.example.phonelocatebackend.exception;


import org.example.phonelocatebackend.model.enums.ErrorCode;

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    /**
     * 构造函数，使用指定的错误码和消息创建 BusinessException 实例
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数，使用指定的 ErrorCode 创建 BusinessException 实例
     * @param errorCode 错误码枚举
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 构造函数，使用指定的 ErrorCode 和消息创建 BusinessException 实例
     * @param errorCode 错误码枚举
     * @param message 错误消息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public int getCode() {
        return code;
    }
}
