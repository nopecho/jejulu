package hello.jejulu.web.interceptor;

import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HostAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            String requestURI = request.getRequestURI();
            response.sendRedirect("/login?uri="+requestURI);
            return false;
        }
        Object attribute = session.getAttribute(SessionConst.HOST);
        if (attribute == null) {
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        return true;
    }
}
