package hello.jejulu.web.controller.host;


import hello.jejulu.domain.Role;
import hello.jejulu.domain.host.Host;
import hello.jejulu.service.host.HostServiceImpl;
import hello.jejulu.web.controller.host.hostFrom.HostSignForm;
import hello.jejulu.web.dto.HostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/hosts")
@Controller
@RequiredArgsConstructor
public class HostController {


    private final HostServiceImpl hostService;

    @GetMapping("/sign-up")
    public String hostSaveForm(@ModelAttribute(name = "save")HostDto.Save host){
        return "jejulu/sign/sign-up-host-form";
    }

    @PostMapping
    public String hostSignForm(@Validated @ModelAttribute HostSignForm host, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult.getFieldError().getField());
            return "redirect:/";

        }

        Host signedHost = HostSignForm.signHost(host);
        hostService.join(signedHost);
        log.info("signedHost name={}", signedHost.getHostName());


        return "redirect:/";
    }


}
