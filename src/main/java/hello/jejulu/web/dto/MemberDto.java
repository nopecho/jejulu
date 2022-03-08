package hello.jejulu.web.dto;

import hello.jejulu.domain.util.Role;
import hello.jejulu.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter @Setter
public class MemberDto {

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String email;
    private LocalDate createDate;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.createDate = LocalDate.from(member.getCreateDate());
    }

    @Getter @Setter
    public static class Info {
        private Long id;
        private String loginId;
        private String name;

        public Info(Member member) {
            this.id = member.getId();
            this.loginId = member.getLoginId();
            this.name = member.getName();
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
        private String name;

        @NotBlank
        @Size(min = 10, max = 13)
        private String phone;

        @Email
        private String email;

        public Member toEntity(PasswordEncoder passwordEncoder) {
            return Member.builder()
                    .loginId(this.loginId)
                    .password(passwordEncoder.encode(this.password))
                    .name(this.name)
                    .phone(this.phone)
                    .email(this.email)
                    .role(Role.MEMBER)
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
