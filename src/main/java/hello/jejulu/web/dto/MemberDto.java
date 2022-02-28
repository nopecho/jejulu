package hello.jejulu.web.dto;

import hello.jejulu.domain.Role;
import hello.jejulu.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberDto {

    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String email;

    public MemberDto(Member member){
        this.loginId = member.getLoginId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.email = member.getEmail();
    }

    @Getter @Setter
    public static class Save{
        @NotBlank
        private String loginId;

        @NotBlank
        private String password;

        @NotBlank
        private String name;

        @NotBlank
        private String phone;

        private String email;

        public Member toEntity(PasswordEncoder passwordEncoder){
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
}
