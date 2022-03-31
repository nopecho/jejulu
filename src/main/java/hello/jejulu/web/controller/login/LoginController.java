package hello.jejulu.web.controller.login;

import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.host.Host;
import hello.jejulu.service.host.HostServiceImpl;
import hello.jejulu.service.web.LoginService;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.controller.host.hostDto.HostLoginDto;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Slf4j
@Controller
public class LoginController implements SessionConst{

    private final LoginService loginService;
    private final HostServiceImpl hostService;

    /**
     * 로그인 폼 페이지 요청 핸들러
     * @param loginDto : Form요청에서 넘어오는 로그인 정보 바인딩 될 객체
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginDto loginDto){
        return "jejulu/login/login-form";
    }

    /**
     * 회원 로그인 핸들러
     * @param loginDto : From요청의 input 값이랑 해당 객체 필드랑 바인딩 객체
     * @param bindingResult : 로그인 실패 시 오류 담을 객체
     * @param request : 로그인 성공 시 request객체의 세션 조회 후 로그인 정보 세션에 저장
     */
    @PostMapping("/login/member")
    public String memberLogin(@ModelAttribute LoginDto loginDto,
                              BindingResult bindingResult,
                              HttpServletRequest request,
                              @RequestParam(defaultValue = "/") String uri){
        MemberDto.Info loginMember = loginService.loginByMember(loginDto);
        if(loginMember == null){
            bindingResult.reject("loginFail.Member");
            return "jejulu/login/login-form";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.MEMBER,loginMember);
        return "redirect:"+uri;
    }

    /**
     * 호스트 로그인 핸들러
     * @param form
     * @param request
     * @return
     */

    @PostMapping("/login/host")
    public String hostLogin(@Validated @ModelAttribute HostLoginDto form,
                            BindingResult bindingResult,
                            @RequestParam(defaultValue = "/")String redirectURL,
                            HttpServletRequest request){

        // 에러있으면 다시 홈으로 리턴
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult.getTarget());
            return "redirect:/";

        }

        Host loginHost = hostService.hostLogin(form.getLoginId(), form.getPassword());

        if(loginHost==null){
            bindingResult.reject("loginFail.Host","아이디 또는 비밀번호가 맞지 않습니다"); //글로벌 오류
            log.info("errors1={}",bindingResult.getFieldError());
            return "redirect:/login";
        }

        HttpSession session = request.getSession();
        if(session.getAttribute(SessionConst.HOST)==null){
            session.setAttribute(SessionConst.HOST,loginHost);
        }else{
            return "redirect:/";
        }

        return "redirect:/";
    }


    /**
     * 관리자 로그인 핸들러
     * @param loginDto : From요청의 input 값이랑 해당 객체 필드랑 바인딩 객체
     * @param bindingResult : 로그인 실패 시 오류 담을 객체
     * @param request : 로그인 성공 시 request객체의 세션 조회 후 로그인 정보 세션에 저장
     */
    @PostMapping("/login/admin")
    public String adminLogin(@ModelAttribute LoginDto loginDto,
                             BindingResult bindingResult,
                             HttpServletRequest request){
        Admin admin = loginService.loginByAdmin(loginDto);
        if(admin == null){
            bindingResult.reject("loginFail.Admin");
            return "jejulu/admin/admin-login-form";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.ADMIN,admin);
        return "redirect:/";
    }

    /**
     * 로그아웃 핸들러
     * @param request : 요청 객체 세션 조회 후 세션 만료
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        session.invalidate();
        return "redirect:/";
    }
}
