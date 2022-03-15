package hello.jejulu.web.annotation;

import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.dto.MemberDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = MemberDto.Info.class.isAssignableFrom(parameter.getParameterType());
        boolean hasHostType = HostDto.Info.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasLoginType(hasMemberType,hasHostType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        String loginType = session.getAttributeNames().nextElement();
        return session.getAttribute(loginType);
    }

    private boolean hasLoginType(boolean member, boolean host){
        return member || host;
    }
}
