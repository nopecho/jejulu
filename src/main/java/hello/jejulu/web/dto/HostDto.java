package hello.jejulu.web.dto;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.util.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class HostDto {

    @Getter @Setter
    public static class Info{
        private Long id;
        private String loginId;
        private String name;

        public Info(Host host){
            this.id = host.getId();
            this.loginId = host.getLoginId();
            this.name = host.getName();
        }
    }

    @Getter @Setter
    public static class Detail {
        private Long id;
        private String name;
        private String phone;
        private String email;
        private String addr;
        private LocalDateTime updateDate;

        public Detail(Host host){
            this.id = host.getId();
            this.name = host.getName();
            this.phone = host.getPhone();
            this.email = host.getEmail();
            this.addr = host.getAddr();
            this.updateDate = host.getModifiedDate();
        }
    }

    @Getter @Setter
    public static class Save{
        @NotBlank
        @Size(min = 4, max = 20)
        private String loginId;

        @NotBlank
        @Size(min = 6, max = 20)
        private String password;

        @NotBlank
        @Size(min = 2, max = 10)
        private String name;

        @NotBlank
        private String addr;

        @NotBlank
        @Size(min = 10, max = 13)
        private String phone;

        @Email
        private String email;

        public Host toEntity(PasswordEncoder passwordEncoder){
            return Host.builder()
                    .loginId(this.loginId)
                    .password(passwordEncoder.encode(this.password))
                    .name(this.name)
                    .addr(this.addr)
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
        @Size(min = 4, max = 20)
        private String name;

        @NotBlank
        @Size(min = 10, max = 13)
        private String phone;

        @NotBlank
        private String addr;

        @Email
        private String email;
    }
}
