package org.zeroframework.boot.log;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口调用信息类，用于接口调用日志记录
 *
 * @param <T>
 */
@Data
public class InvokerLog<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 访问host
     */
    private String host;

    /**
     * 接口类名称
     */
    private String interfaceClass;

    /**
     * 接口方法名称
     */
    private String methodName;

    /**
     * 接口方法参数
     */
    private String parameters;

    /**
     * 当前用户
     */
    private String userName;

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 调用方法url，主要用于web
     */
    private String url;

    /**
     * 接口开始时间
     */
    private String startTimeStr;

    /**
     * 接口结束时间
     */
    private String endTimeStr;

    /**
     * 接口总耗时,单位为毫秒/ms
     */
    private Long executeTime;

    /**
     * 接口返回状态码
     */
    private Integer code = 200;

    /**
     * 接口返回状态信息，接口正常返回时message为null
     */
    private String message;

    /**
     * 接口正常返回时的结果
     */
    private T data;

}
