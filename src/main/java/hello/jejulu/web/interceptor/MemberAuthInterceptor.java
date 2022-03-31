package hello.jejulu.web.interceptor;

import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            String requestURI = request.getRequestURI();
            response.sendRedirect("/login?uri="+requestURI);
            return false;
        }
        MemberDto.Info loginMember = (MemberDto.Info) session.getAttribute(SessionConst.MEMBER);
        if (loginMember == null) {
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        return true;
    }
}
