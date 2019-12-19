package org.zeroframework.boot.message;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于web层返回给前端的数据结构
 *
 * @param <T>
 * @author lijianqing
 */
@Data
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code = 200;

    /**
     * 状态信息
     */
    private String message = "OK";

    /**
     * 请求url,用于异常时记录
     */
    private String url;

    /**
     * 返回的结果泛型
     */
    private T data;

    /**
     * 成功
     *
     * @return
     */
    public static <T> ResultDTO<T> success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static <T> ResultDTO<T> success(T data) {
        ResultDTO<T> rb = new ResultDTO<>();
        rb.setCode(ResultCodeEnum.SUCCESS.getCode());
        rb.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> ResultDTO<T> error(BaseErrorInfoInterface errorInfo) {
        ResultDTO<T> rb = new ResultDTO<>();
        rb.setCode(errorInfo.getCode());
        rb.setMessage(errorInfo.getMessage());
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> ResultDTO<T> error(Integer code, String message) {
        ResultDTO<T> rb = new ResultDTO<>();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> ResultDTO<T> error(String message) {
        ResultDTO<T> rb = new ResultDTO<>();
        rb.setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

}
