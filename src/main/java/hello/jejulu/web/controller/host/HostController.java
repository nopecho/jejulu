package hello.jejulu.web.controller.host;


import hello.jejulu.domain.host.Host;
import hello.jejulu.repository.HostRepository_B;
import hello.jejulu.service.host.HostServiceImpl;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.controller.host.hostDto.HostSignDto;
import hello.jejulu.web.controller.host.hostDto.HostUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("/hosts")
@Controller
@RequiredArgsConstructor
public class HostController {


    private final HostServiceImpl hostService;
    private final HostRepository_B hostRepository_B;

    //회원가입 폼 요청
    @GetMapping("/sign-up")
    public String hostSaveForm(@ModelAttribute(name = "save") HostSignDto host){
        return "jejulu/sign/sign-up-host-form";
    }

    //회원가입
    @PostMapping
    public String hostSignForm(@Validated @ModelAttribute HostSignDto host,
                               BindingResult bindingResult,HttpServletRequest request){

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult.getFieldError().getField());
            return "redirect:/";

        }

        Host signedHost = HostSignDto.signHost(host);
        Host joinedHost = hostService.join(signedHost);
        log.info("signedHost name={}", signedHost.getHostName());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.HOST,joinedHost);


        return "redirect:/";
    }

    //호스트 정보수정
    @GetMapping("/info")
    public String searchHost(HttpServletRequest request,
                             RedirectAttributes redirectAttributes){

        HttpSession session = request.getSession(false);
        Host loginHost = (Host)session.getAttribute(SessionConst.HOST);

        if(loginHost==null){
            return "redirect:/login";
        }
        Long id =loginHost.getId();
        redirectAttributes.addAttribute("hostId",id);
        return "redirect:/hosts/{hostId}";

    }

    //호스트 정보수정 페이지
    @GetMapping("/{hostId}")
    public String hostPage(@PathVariable Long hostId,
                               Model model){
        Host findHost = hostRepository_B.findByPk(hostId);
        model.addAttribute("detail", findHost);
        return "jejulu/hosts/host";
    }


    //호스트 회원 정보 수정 페이지 요청
    @GetMapping("/{hostId}/edit")
    public String hostEditPage(@PathVariable Long hostId,
                               Model model){
        Host findHost = hostRepository_B.findByPk(hostId);
        HostUpdateDto hostForUpdate = HostUpdateDto.updateHost(findHost);
        model.addAttribute("update", hostForUpdate);
        return "jejulu/hosts/host-update-form";
    }


    //호스트 회원정보 수정 요청
    @PatchMapping("/{hostId}")
    public String updateHost(@Validated @ModelAttribute HostUpdateDto form,
                             @PathVariable Long hostId,
                             BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult.getFieldError());
            return"jejulu/hosts/host-update-form";
        }
        hostService.updateHost(hostId,form.getHostName(),form.getAddress(),form.getPhone(),form.getEmail());

        return "redirect:/hosts/{hostId}";
    }


    /**
     * 호스트 탈퇴
     * @param hostId
     * @param request
     * @return
     */
    @DeleteMapping("/{hostId}")
    public String deleteHost(@PathVariable Long hostId,
                             HttpServletRequest request){
        hostService.deleteHost(hostId);

        HttpSession session = request.getSession(false);
        if(session!=null){
           session.invalidate();
        }
        return "redirect:/";
    }


}
