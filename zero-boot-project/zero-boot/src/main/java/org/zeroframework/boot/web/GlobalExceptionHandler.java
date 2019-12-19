package org.zeroframework.boot.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zeroframework.boot.exception.BaseException;
import org.zeroframework.boot.message.ResultCodeEnum;
import org.zeroframework.boot.message.ResultDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Web 服务全局异常处理器, 统一返回 ResultDTO
 */
@Slf4j
@ControllerAdvice
@Order(1)
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResultDTO<T> baseExceptionHandler(HttpServletRequest req, BaseException e) {
        log.error("发生业务异常！", e);
        int code = Objects.isNull(e.getCode()) ? ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode() : e.getCode();
        String message = StringUtils.isBlank(e.getMessage()) ? ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage(): e.getMessage();
        return ResultDTO.error(code, message);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResultDTO<T> runtimeExceptionHandler(HttpServletRequest req, RuntimeException e) {
        log.error("发生运行时异常！", e);
        return ResultDTO.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理空指针的异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResultDTO<T> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！", e);
        return ResultDTO.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 处理其他异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResultDTO<T> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！", e);
        return ResultDTO.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO<String> handlerBindException(HttpServletRequest request, BindException e) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
            sb.append(fe.getField()).append(":").append(fe.getDefaultMessage()).append(";");
        }
        String errorStr = sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
        return ResultDTO.error(HttpStatus.BAD_REQUEST.value(), errorStr);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO<String> handlerMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
            sb.append(fe.getField()).append(":").append(fe.getDefaultMessage()).append(";");
        }
        String errorStr = sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
        return ResultDTO.error(HttpStatus.BAD_REQUEST.value(), errorStr);
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultDTO<String> handlerSQLException(SQLException e) {
        log.error("数据库异常！", e);
        return ResultDTO.error(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }
}
