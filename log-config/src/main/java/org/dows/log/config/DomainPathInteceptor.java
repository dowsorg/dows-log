package org.dows.log.config;

import org.dows.log.api.annotation.DomainMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public class DomainPathInteceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            DomainMapping methodAnnotation = handlerMethod.getMethodAnnotation(DomainMapping.class);
            if (methodAnnotation != null) {
                Set<String> keys = ((Map<String, String>) request
                        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).keySet();
                if (keys.contains("domain")) {
                    return true;
                } else {
                    throw new RuntimeException("不存在的路径");
                }
                /*for (MethodParameter mp : handlerMethod.getMethodParameters()) {
                    PathVariable parameterAnnotation = mp.getParameterAnnotation(PathVariable.class);
                    if (parameterAnnotation != null) {
                        String value = parameterAnnotation.value();
                        if (value.equals("domain") && keys.contains(value)) {
                            return true;
                        }
                    }
                }*/
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
