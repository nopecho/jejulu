package hello.jejulu.web.dto;

import hello.jejulu.domain.Role;
import hello.jejulu.domain.host.Host;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
public class HostDto {

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String addr;
    private LocalDate createDate;

    public HostDto(Host host) {
        this.id = host.getId();
        this.loginId = host.getLoginId();
        this.password = host.getPassword();
        this.name = host.getHostName();
        this.phone = host.getPhone();
        this.email = host.getEmail();
        this.createDate = LocalDate.from(host.getCreateDate());
    }

    @Getter @Setter
    public static class Info {
        private Long id;
        private String loginId;
        private String name;

        public Info(Host host) {
            this.id = host.getId();
            this.loginId = host.getLoginId();
            this.name = host.getHostName();
        }
    }

    @Getter @Setter
    public static class Save {
        @NotBlank
        @Size(min = 4, max = 20)
        private String loginId;

        @NotBlank
        @Size(min = 6, max = 20)
        private String password;

        @NotBlank
        @Size(min = 2, max = 10)
        private String hostname;

        @NotBlank
        @Size(min = 10, max = 13)
        private String phone;

        @Email
        private String email;

        public Host toEntity(PasswordEncoder passwordEncoder) {
            return Host.builder()
                    .loginId(this.loginId)
                    .password(passwordEncoder.encode(this.password))
                    .hostName(this.hostname)
                    .phone(this.phone)
                    .email(this.email)
                    .role(Role.HOST)
                    .build();
        }
    }

    @Getter @Setter
    public static class Update {

        private Long id;

        @NotBlank
        @Size(min = 2, max = 10)
        private String name;

        @NotBlank
        @Size(min = 10, max = 13)
        private String phone;

        @Email
        private String email;
    }
}
