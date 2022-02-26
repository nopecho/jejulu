package hello.jejulu.service.Admin;

import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin(){
        Admin admin = Admin.builder()
                .loginId("admin")
                .password(passwordEncoder.encode("admin"))
                .build();
        adminRepository.save(admin);
    }
}
