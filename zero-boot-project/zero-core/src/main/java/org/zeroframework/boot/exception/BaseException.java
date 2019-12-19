package org.zeroframework.boot.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

    private Integer code;

    private String message;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }
}
