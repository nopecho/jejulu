package hello.jejulu.web.controller.host;

import hello.jejulu.service.host.HostService;
import hello.jejulu.web.dto.HostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/hosts")
public class HostController {

    private final HostService hostService;

    @GetMapping("/sign-up")
    public String hostSaveForm(@ModelAttribute HostDto.Save hostSaveDto){
        return "jejulu/sign/sign-up-host-form";
    }

    @ResponseBody
    @GetMapping("id-check")
    public boolean hostIdCheck(@RequestParam(name = "v") String checkId){
        return hostService.isDuplicateId(checkId);
    }

    @PostMapping
    public String hostSave(@ModelAttribute @Validated HostDto.Save hostSaveDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "jejulu/sign/sign-up-host-form";
        }
        HostDto.Info saveHost = hostService.add(hostSaveDto);
        redirectAttributes.addAttribute("name",saveHost.getName());
        return "redirect:/success/sign-up";
    }
}
