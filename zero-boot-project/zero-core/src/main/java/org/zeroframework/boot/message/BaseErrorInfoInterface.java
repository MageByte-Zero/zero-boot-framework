package org.zeroframework.boot.message;

/**
 * web接口 code message 定义，编译自定义拓展状态码
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     */
    Integer getCode();

    /**
     * 错误描述
     */
    String getMessage();
}