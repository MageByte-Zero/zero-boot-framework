package org.zeroframework.boot.web;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zeroframework.boot.exception.BaseException;
import org.zeroframework.boot.log.InvokerLog;
import org.zeroframework.boot.message.ResultCodeEnum;
import org.zeroframework.boot.util.AspectUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
public class WebLogAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) " +
            "|| @within(org.springframework.stereotype.Controller)")
    public void webLogPointcut() {
    }

    /**
     * 接口调用时的 around方法，记录调用信息及异常信息
     *
     * @param joinPoint 切面的连接点
     * @return 执行结果，如果执行失败会返回ResultDTO结果
     * @throws Throwable
     */
    @Around(value = "webLogPointcut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();
        InvokerLog invoker = initInvokerMessage(joinPoint, startTime, result);
        try {
            result = joinPoint.proceed();
        } catch (BaseException e) {
            invoker.setCode(e.getCode());
            String errorMsg = e.getMessage() == null ? "" : e.getMessage();
            invoker.setMessage(errorMsg);
            log.error("Web接口调用失败，异常类型为BaseException, 失败详情: {}", JSONObject.toJSONString(invoker), e);
            throw new BaseException(e.getCode(), errorMsg, e);
        } catch (Exception e) {
            invoker.setCode(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode());
            String errorMsg = (e.getMessage() == null) ? ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage();
            invoker.setMessage(errorMsg);
            log.error("Web接口调用失败，异常类型为{}, 失败详情: {}", e.getClass().getSimpleName(), JSONObject.toJSONString(invoker), e);
            throw e;
        }

        log.info("Web接口调用成功，接口调用详情: {}", JSONObject.toJSONString(invoker));
        return result;
    }

    private InvokerLog initInvokerMessage(ProceedingJoinPoint joinPoint, Long startTime, Object result) {
        InvokerLog invoker = null;
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            invoker = AspectUtils.formatInvokerResultMsg(joinPoint, startTime, result, request);
        } catch (Exception e) {
            log.error("封装Web接口调用详情失败，audit={}", e.getMessage(), e);
        }
        return invoker;
    }
}
