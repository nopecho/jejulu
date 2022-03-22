package hello.jejulu.web.controller.admin;

import hello.jejulu.service.admin.AdminService;
import hello.jejulu.service.host.HostService;
import hello.jejulu.service.member.MemberService;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.dto.member.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final HostService hostService;

    @GetMapping("/login")
    public String adminLoginForm(@ModelAttribute LoginDto loginDto) {
        return "jejulu/admin/admin-login-form";
    }

    @GetMapping("/management")
    public String management() {
        return "jejulu/admin/management";
    }

    @GetMapping("/management/members")
    public String managementMembers(@PageableDefault(size = 15, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                    Model model) {
        Page<MemberDto.AdminDetail> page = adminService.getMembersForAdmin(pageable);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);
        return "jejulu/admin/management-members";
    }

    @ResponseBody
    @DeleteMapping("/members/{memberId}")
    public boolean adminMemberDelete(@PathVariable Long memberId) {
        return memberService.remove(memberId);
    }

    @GetMapping("/management/hosts")
    public String managementHosts(@PageableDefault(size = 15, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {
        Page<HostDto.AdminDetail> page = adminService.getHostsForAdmin(pageable);
        model.addAttribute("page",page);
        model.addAttribute("maxPage",10);
        return "jejulu/admin/management-hosts";
    }

    @ResponseBody
    @DeleteMapping("/hosts/{hostId}")
    public boolean adminHostDelete(@PathVariable Long hostId) {
        return hostService.remove(hostId);
    }

    @GetMapping("/management/posts")
    public String managementPosts(Model model) {
        model.addAttribute("posts", null);
        return "jejulu/admin/management-posts";
    }

    @GetMapping("/management/contacts")
    public String managementContacts(Model model) {
        model.addAttribute("contacts", null);
        return "jejulu/admin/management-contact";
    }
}
