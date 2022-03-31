package hello.jejulu.web.dto.contact;

import hello.jejulu.domain.contact.Contact;
import hello.jejulu.domain.util.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter @Setter
public class ContactDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
    private Role role;
    private String loginId;

    public Contact toEntity(Role role, String loginId){
        return Contact.builder()
                .title(this.title)
                .content(this.content)
                .role(role)
                .loginId(loginId)
                .build();
    }

    @Getter @Setter
    public static class Detail{
        private Long id;
        private String title;
        private String content;
        private Role role;
        private String loginId;
        private LocalDate createDate;


        public Detail(Contact contact){
            this.id = contact.getId();
            this.title = contact.getTitle();
            this.content = contact.getContent();
            this.role = contact.getRole();
            this.loginId = contact.getLoginId();
            this.createDate = contact.getCreateDate().toLocalDate();
        }
    }
}
