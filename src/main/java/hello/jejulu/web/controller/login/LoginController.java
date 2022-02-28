package hello.jejulu.web.controller.login;

import hello.jejulu.service.web.LoginService;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String memberLogin(@ModelAttribute LoginDto loginDto, HttpServletRequest request){
        MemberDto memberDto = loginService.loginByMember(loginDto);
        if(memberDto == null){
            return "redirect:/login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.MEMBER,memberDto);
        return "redirect:/";
    }

    @PostMapping("/login/host")
    public String hostLogin(@ModelAttribute LoginDto loginDto){
        return null;
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
