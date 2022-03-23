package hello.jejulu.web.dto.contact;

import hello.jejulu.domain.contact.Contact;
import hello.jejulu.domain.util.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ContactDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Contact toEntity(Role role, String loginId){
        return Contact.builder()
                .title(this.title)
                .content(this.content)
                .role(role)
                .loginId(loginId)
                .build();
    }
}
