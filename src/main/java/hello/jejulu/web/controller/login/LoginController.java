package hello.jejulu.web.controller.login;

import hello.jejulu.domain.admin.Admin;
import hello.jejulu.service.web.LoginService;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Slf4j
@Controller
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginDto loginDto){
        return "jejulu/login/login-form";
    }

    @PostMapping("/login/member")
    public String memberLogin(@ModelAttribute LoginDto loginDto,
                              BindingResult bindingResult,
                              HttpServletRequest request){
        MemberDto.Info memberInfoDto = loginService.loginByMember(loginDto);
        if(memberInfoDto == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호를 확인해주세요.");
            return "jejulu/login/login-form";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.MEMBER,memberInfoDto);
        return "redirect:/";
    }

    @PostMapping("/login/host")
    public String hostLogin(@ModelAttribute LoginDto loginDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.HOST,"");
        return "redirect:/";
    }

    @PostMapping("/login/admin")
    public String adminLogin(@ModelAttribute LoginDto loginDto, HttpServletRequest request){
        Admin admin = loginService.loginByAdmin(loginDto);
        if(admin == null){
            return null;
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.ADMIN,admin);
        return "redirect:/";
    }

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
