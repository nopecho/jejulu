package hello.jejulu.web.controller.host.hostDto;

import hello.jejulu.domain.Role;
import hello.jejulu.domain.host.Host;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter@Setter
public class HostSignDto {
    @NotBlank
    @Size(min = 4, max = 20)
    private String loginId;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min = 2, max = 10)
    private String hostName;

    @NotBlank
    @Size(min =2 , max=50)
    private String address;

    @NotBlank
    @Size(min = 10, max = 13)
    private String phone;

    @Email
    private String email;



    public static Host signHost(HostSignDto param){
        return Host.builder()
                .loginId(param.getLoginId())
                .password(param.password)
                .hostName(param.getHostName())
                .phone(param.getPhone())
                .email(param.getEmail())
                .role(Role.HOST)
                .address(param.getAddress())
                .build();

    }
}
