package hello.jejulu.service.admin;

import hello.jejulu.domain.util.Role;
import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import hello.jejulu.service.member.MemberService;
import hello.jejulu.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @PostConstruct
    @Transactional
    public void initAdmin(){
        Admin admin = Admin.builder()
                .loginId("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();
        adminRepository.save(admin);
    }

    @Override
    public List<MemberDto> findMembers(){
        List<MemberDto> memberDtos = memberService.selectAll();
        if(memberDtos == null){
            return null;
        }
        return memberDtos;
    }
}
