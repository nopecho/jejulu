package hello.jejulu.web.controller.home;

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
    public String home(Model model){
        log.info((String) model.getAttribute("test"));
        return "jejulu/home/home";
    }

    @ResponseBody
    @GetMapping("/{test}")
    public String test(@PathVariable String test){
        return test;
    }

    @GetMapping("/redirect")
    public String redirect(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("test","kkk123k");
        return "redirect:/{test}";
    }
}
