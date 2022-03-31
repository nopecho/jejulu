package hello.jejulu.web.interceptor;

import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
public class HostAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("호스트인증 인터셉터 실행");
        HttpSession session = request.getSession(false);

        if(session==null || session.getAttribute(SessionConst.HOST)==null){

            log.info("is Not Host");

            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        return true;
    }
}
