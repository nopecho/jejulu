package hello.jejulu.web.controller;

import hello.jejulu.web.dto.BookingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UtilController {

    @GetMapping("/sign-up")
    public String signUpSelect(){
        return "jejulu/sign/sign-select";
    }

    @GetMapping("/success/sign-up")
    public String successSignUp(@RequestParam String name, Model model){
        model.addAttribute("name",name);
        return "jejulu/success/success-sign-up";
    }

    @GetMapping("/success/post/{postId}")
    public String successPost(@PathVariable Long postId, Model model){
        model.addAttribute("id",postId);
        return "jejulu/success/success-post";
    }
}
