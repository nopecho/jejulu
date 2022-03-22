package hello.jejulu.web.controller.host;

import hello.jejulu.service.host.HostService;
import hello.jejulu.web.annotation.Login;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

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
    @GetMapping("/id-check")
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

    @GetMapping("/info")
    public String lookupHost(@Login HostDto.Info loginHost,
                             RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("hostId",loginHost.getId());
        return "redirect:/hosts/{hostId}";
    }

    @GetMapping("/{hostId}")
    public String hostInfo(@PathVariable Long hostId,
                           @Login HostDto.Info loginHost,
                           Model model){
        if(!loginHost.getId().equals(hostId)){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        HostDto.Detail lookupHost = hostService.getHostById(hostId);
        model.addAttribute("detail",lookupHost);
        return "jejulu/hosts/host";
    }

    @GetMapping("/{hostId}/edit")
    public String hostUpdateForm(@PathVariable Long hostId, Model model){
        HostDto.Detail lookupHost = hostService.getHostById(hostId);
        model.addAttribute("update",lookupHost);
        model.addAttribute("id",hostId);
        return "jejulu/hosts/host-update-form";
    }

    @PatchMapping("/{hostId}")
    public String updateHost(@PathVariable Long hostId,
                             @ModelAttribute @Validated HostDto.Update hostUpdateDto,
                             BindingResult bindingResult,
                             Model model,
                             HttpSession session){
        if (bindingResult.hasErrors()){
            model.addAttribute("id",hostId);
            return "jejulu/hosts/host-update-form";
        }
        HostDto.Info updateHost = hostService.edit(hostId, hostUpdateDto);
        session.setAttribute(SessionConst.HOST,updateHost);
        return "redirect:/hosts/{hostId}";
    }

    @DeleteMapping("/{hostId}")
    public String removeHost(@PathVariable Long hostId, HttpSession session){
        if(!hostService.remove(hostId)){
            throw new CustomException(ErrorCode.HOST_REMOVE_FAIL);
        }
        session.invalidate();
        return "redirect:/";
    }
}
