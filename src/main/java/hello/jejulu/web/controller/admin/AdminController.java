package hello.jejulu.web.controller.admin;

import hello.jejulu.service.admin.AdminService;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
