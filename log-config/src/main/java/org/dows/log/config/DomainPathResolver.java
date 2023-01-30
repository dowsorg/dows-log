package org.dows.log.config;

import org.dows.log.api.DomainMetadata;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class DomainPathResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //方法参数是 DomainMeta, 则使用此解析器
        return DomainMetadata.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 获取请求
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String info = (String) webRequest.getAttribute("params", NativeWebRequest.SCOPE_REQUEST);
        DomainMetadata domainMetadata = DomainMetadata.builder().build();

        //
        return domainMetadata;
    }
}
