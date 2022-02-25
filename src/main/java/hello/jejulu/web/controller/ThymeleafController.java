package hello.jejulu.web.controller;

import hello.jejulu.web.consts.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class ThymeleafController {

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.ADMIN,"조현준");

        model.addAttribute("MEMBER",session.getAttribute(SessionConst.MEMBER));
        return "jejulu/home";
    }
    @GetMapping("/logout")
    public String memberHome(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/homes";
    }
    @GetMapping("/homes")
    public String hostHome(){
        log.info("login member = {}");
        return "jejulu/home";
    }
    @GetMapping("/login")
    public String adminHome(){
        return "jejulu/login/login-form";
    }
    @GetMapping("/booking")
    public String test(){
        return "jejulu/bookings/booking-form";
    }
    @GetMapping("/about")
    public String about(){
        return "jejulu/etc/about";
    }
    @GetMapping("/1")
    public String test1(){
        return "jejulu/etc/contact-form";
    }
    @GetMapping("/2")
    public String test2(){
        return "jejulu/success/success-bookings";
    }
    @GetMapping("/3")
    public String test3(){
        return "jejulu/success/success-post";
    }
    @GetMapping("/4")
    public String test4(){
        return "jejulu/success/success-sign-up";
    }
    @GetMapping("/5")
    public String test5(){
        return "jejulu/hosts/host-update-form";
    }
    @GetMapping("/6")
    public String test6(){
        return "jejulu/posts/posts-host";
    }
    @GetMapping("/7")
    public String test7(){
        return "jejulu/members/member-update-form";
    }
    @GetMapping("/8")
    public String test8(){
        return "jejulu/members/member";
    }
    @GetMapping("/9")
    public String test9(){
        return "jejulu/hosts/host";
    }
    @GetMapping("/10")
    public String test10(){
        return "jejulu/bookings/bookings-member";
    }
    @GetMapping("/11")
    public String test11(){
        return "jejulu/posts/post";
    }
    @GetMapping("/12")
    public String test12(){
        return "jejulu/posts/post-form";
    }
    @GetMapping("/13")
    public String test13(){
        return "jejulu/posts/post-update-form";
    }
    @GetMapping("/14")
    public String test14(){
        return "jejulu/posts/posts-host";
    }
    @GetMapping("/15")
    public String test15(){
        return "jejulu/sign/sign-up-host-form";
    }
    @GetMapping("/16")
    public String test16(){
        return "jejulu/sign/sign-up-member-form";
    }
    @GetMapping("/17")
    public String test17(){
        return "jejulu/sign/sign-select";
    }
    @GetMapping("/18")
    public String test18(){
        return "jejulu/admin/management-hosts";
    }
    @GetMapping("/19")
    public String test19(){
        return "jejulu/admin/management-members";
    }
    @GetMapping("/20")
    public String test20(){
        return "jejulu/admin/management-posts";
    }
    @GetMapping("/21")
    public String test21(){
        return "jejulu/admin/management-contact";
    }
    @GetMapping("/22")
    public String test22(){
        return "jejulu/sign/sign-select";
    }

    @ResponseBody
    @GetMapping("hosts/id-check")
    public boolean check(@RequestParam String v){
        if(v.equals("test")){
            return true;
        }
        return false;
    }
}
