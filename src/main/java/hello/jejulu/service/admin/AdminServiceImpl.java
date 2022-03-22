package hello.jejulu.service.admin;

import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.domain.util.Role;
import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import hello.jejulu.web.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final MemberRepository memberRepository;

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
    public Page<MemberDto.AdminDetail> getMembersForAdmin(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberDto.AdminDetail::new);
    }
}
