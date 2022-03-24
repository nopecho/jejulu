package hello.jejulu.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "jejulu/home";
    }

    @GetMapping("/sign-up")
    public String signUpSelect(){
        return "jejulu/sign/sign-select";
    }
}
