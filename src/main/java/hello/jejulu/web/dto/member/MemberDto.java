package hello.jejulu.web.dto.member;

import hello.jejulu.domain.util.Role;
import hello.jejulu.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberDto {

    @Getter @Setter
    public static class AdminDetail {
        private Long id;
        private String loginId;
        private String name;
        private String phone;
        private String email;
        private LocalDateTime createDate;

        public AdminDetail(Member member) {
            this.id = member.getId();
            this.loginId = member.getLoginId();
            this.name = member.getName();
            this.phone = member.getPhone();
            this.email = member.getEmail();
            this.createDate = member.getCreateDate();
        }
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
    public static class Detail {
        private Long id;
        private String name;
        private String phone;
        private String email;
        private LocalDateTime updateDate;

        public Detail(Member member){
            this.id = member.getId();
            this.name = member.getName();
            this.phone = member.getPhone();
            this.email = member.getEmail();
            this.updateDate = member.getModifiedDate();
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
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름에 공백, 숫자, 특수문자가 들어갈 수 없어요")
        private String name;

        @NotBlank
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식을 다시 확인 해주세요")
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
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름에 공백, 숫자, 특수문자가 들어갈 수 없어요")
        private String name;

        @NotBlank
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식을 다시 확인 해주세요")
        private String phone;

        @Email
        private String email;
    }
}
