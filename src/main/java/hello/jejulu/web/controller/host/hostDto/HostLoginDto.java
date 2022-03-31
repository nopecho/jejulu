package hello.jejulu.web.controller.host.hostDto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter@Setter

public class HostLoginDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
