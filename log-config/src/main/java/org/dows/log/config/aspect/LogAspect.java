package org.dows.log.config.aspect;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dows.log.api.DomainContextHolder;
import org.dows.log.api.DomainMetadata;
import org.dows.log.api.InsertService;
import org.dows.log.api.annotation.AuditLog;
import org.dows.log.api.util.IpUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;

@RequiredArgsConstructor
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final InsertService insertService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(org.dows.log.api.annotation.AuditLog)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        currentTime.set(System.currentTimeMillis());
        Object result = joinPoint.proceed();
        final Long aLong = currentTime.get();
        currentTime.remove();
        saveLog(joinPoint, "INFO", System.currentTimeMillis() - aLong, "NULL");
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String rootExceptionName = e.getClass().getName();
        StringBuilder resultContent = new StringBuilder("异常类：" + rootExceptionName);
        int count = 0;
        int maxTrace = 3;
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getClassName().contains("org.dows") && count < maxTrace) {
                resultContent.append("\n出现于").append(stackTraceElement.getClassName())
                        .append("类中的").append(stackTraceElement.getMethodName())
                        .append("方法中 位于该类文件的第").append(stackTraceElement.getLineNumber())
                        .append("行)");
                count++;
                if (count == maxTrace) {
                    break;
                }
            }
        }
        saveLog(joinPoint, "ERROR", time, resultContent.toString());
    }

    /**
     * 保持审计日志
     *
     * @param joinPoint
     * @param logTyp
     * @param time
     * @param ex
     */
    private void saveLog(JoinPoint joinPoint, String logTyp, long time, String ex) {
        HttpServletRequest request = getHttpServletRequest();
        // 获取请求参数
        Map<String, String> requestParam = getRequestParams(request);
        // 获取方法参数
        DomainMetadata domainMetadata = getDomainMetadataByMethodAnnotation(joinPoint, requestParam);
        // 组装对象
        insertService.insert(domainMetadata);
        //logService.save(getUsername(), IpUtil.getBrowser(request), IpUtil.getIp(request), joinPoint, log);
    }

    public String getUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                //throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
            }
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return userDetails.getUsername();
            }
        }catch (Exception e){
            return "error";
        }
        //throw new AuthException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
        return "";
    }


    private HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    /**
     * @param request
     * @return
     * @throws Exception
     * @author tianwyam
     * @description 从POST请求中获取参数
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        // 返回参数
        Map<String, String> params = new HashMap<>();
        // 获取内容格式
        String contentType = request.getContentType();
        if (contentType != null && !contentType.equals("")) {
            contentType = contentType.split(";")[0];
        }
        // form表单格式  表单形式可以从 ParameterMap中获取
        if ("appliction/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
            // 获取参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap != null) {
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    params.put(entry.getKey(), entry.getValue()[0]);
                }
            }
        }
        // json格式 json格式需要从request的输入流中解析获取
        if ("application/json".equalsIgnoreCase(contentType)) {
            // 使用IoUtil 类快速获取输入流内容
            String paramJson = null;
            try {
                paramJson = IoUtil.read(request.getInputStream(), Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            params.putAll(JSONUtil.parseObj(paramJson).toBean(Map.class));
        }
        params.put("account_name", getUsername());
        params.put("browser", IpUtil.getBrowser(request));
        params.put("ip", IpUtil.getIp(request));
        // 设备指纹
        params.put("device_id", "");
        return params;
    }


    private DomainMetadata getDomainMetadataByMethodAnnotation(JoinPoint joinPoint, Map<String, String> params) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuditLog auditLog = method.getAnnotation(AuditLog.class);

        Class aClass = auditLog.tableSchemaClass();
        DomainMetadata domainMetadata = DomainContextHolder.get(aClass);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        params.put("method_name", methodName);
        StringBuilder stringBuilder = new StringBuilder("{");
        //参数值
        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
        //参数名称
        for (Object argValue : argValues) {
            stringBuilder.append(argValue).append(" ");
        }
        // 参数
        params.put("method_params", stringBuilder + " }");
        // 描述
        params.put("method_descr", auditLog.value());
        params.forEach((k, v) -> {
            domainMetadata.setFieldValue(k, v);
        });

        return domainMetadata;
    }


}
