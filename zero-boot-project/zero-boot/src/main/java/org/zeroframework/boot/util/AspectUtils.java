package org.zeroframework.boot.util;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.zeroframework.boot.log.InvokerLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 切面工具类
 */
public class AspectUtils {
    private final static Logger logger = LoggerFactory.getLogger(AspectUtils.class);
    /**
     * 返回切面对应调用方法名
     * @param joinPoint 切面连接点
     */
    public static String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()";
    }
    /**
     * 返回切面对应调用方法参数
     * @param joinPoint 切面连接点
     * @return String 接口的参数及对应值
     */
    public static String getMethodParams(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        try {
            //查找相应方法
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //查找相应方法参数名称
            String[] parameterNames = methodSignature.getParameterNames();
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    Object param = joinPoint.getArgs()[i];
                    if (param == null || param instanceof ModelMap || param instanceof HttpServletRequest || param instanceof HttpServletResponse || param instanceof MultipartFile) {
                        continue;
                    }
                    if (i > 0) {
                        sb.append(",");
                    }
                    boolean isSimple = BeanUtils.isSimpleValueType(param.getClass());
                    if (isSimple) {
                        sb.append(parameterNames[i]).append(":").append(param);
                    } else {
                        sb.append(parameterNames[i]).append(":").append(JSONObject.toJSONString(param));
                    }
                }
            }
        }catch (Exception e){
            logger.error("获取切面连接点的方法参数失败", e);
        }
        return sb.toString();
    }
    /**
     * 格式化接口调用结果
     * @param joinPoint 切面连接点
     * @param startTime 接口调用的开始时间
     * @param result 方法返回的result
     * @param request web的HttpServletRequest
     * @return InvokerResultMsg 接口调用结果的信息
     */
    public static InvokerLog formatInvokerResultMsg(JoinPoint joinPoint, Long startTime, HttpServletRequest request) {
        InvokerLog invoker = new InvokerLog();
        long endTime = System.currentTimeMillis();
        try {
            invoker.setInterfaceClass(joinPoint.getSignature().getDeclaringTypeName());
            invoker.setMethodName(joinPoint.getSignature().getName());
            invoker.setParameters(AspectUtils.getMethodParams(joinPoint));
            String url = request.getRequestURL().toString();
            invoker.setUrl(url);
            long executeTime = endTime - startTime;
            LocalDateTime startLocalDateTime = Instant.ofEpochMilli(startTime).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
            LocalDateTime endLocalDateTime = Instant.ofEpochMilli(endTime).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
            invoker.setStartTimeStr(startLocalDateTime.toString());
            invoker.setEndTimeStr(endLocalDateTime.toString());
            invoker.setExecuteTime(executeTime);
        } catch (Exception e) {
            logger.error("格式化接口调用结果失败，audit={}", e.getMessage(), e);
        }
        return invoker;
    }
}