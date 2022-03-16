package hello.jejulu.web.annotation;

import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
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
        return resolveLoginSession(parameter.getParameterType(), session);
    }

    private boolean hasLoginType(boolean member, boolean host){
        return member || host;
    }

    private Object resolveLoginSession(Class<?> paramType, HttpSession session){
        if (paramType.isAssignableFrom(HostDto.Info.class)){
            return session.getAttribute(SessionConst.HOST);
        }
        if (paramType.isAssignableFrom(MemberDto.Info.class)){
            return session.getAttribute(SessionConst.MEMBER);
        }
        throw new CustomException(ErrorCode.REQUIRED_LOGIN);
    }
}
