package hello.jejulu.web.controller.admin;

import hello.jejulu.service.admin.AdminService;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/login")
    public String adminLoginForm(@ModelAttribute LoginDto loginDto){
        return "jejulu/admin/admin-login-form";
    }

    @GetMapping("/management")
    public String management(){
        return "jejulu/admin/management";
    }

    @GetMapping("/management/members")
    public String managementMembers(Model model){
        List<MemberDto> members = adminService.findMembers();
        model.addAttribute("members",members);
        return "jejulu/admin/management-members";
    }

    @GetMapping("/management/hosts")
    public String managementHosts(){
        return "jejulu/admin/management-hosts";
    }

    @GetMapping("/management/posts")
    public String managementPosts(){
        return "jejulu/admin/management-posts";
    }

    @GetMapping("/management/contacts")
    public String managementContacts(){
        return "jejulu/admin/management-contact";
    }
}
