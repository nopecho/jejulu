package hello.jejulu.security;

import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PasswordEncodeTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void 비밀번호_암호화_테스트(){
        //given
        Optional<Admin> admin = adminRepository.findByLoginId("admin");
        String password = admin.orElse(null).getPassword();

        //when
        boolean result = passwordEncoder.matches("admin", password);

        //then
        assertThat(result).isTrue();
    }
}
