package hello.jejulu.web.controller.host.hostDto;


import hello.jejulu.domain.host.Host;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter@Setter
@Builder
public class HostUpdateDto {


    private Long id;

    @NotBlank
    private String hostName;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    public static HostUpdateDto updateHost(Host host){
        return HostUpdateDto.builder()
                .hostName(host.getHostName())
                .address(host.getAddress())
                .phone(host.getPhone())
                .email(host.getEmail())
                .id(host.getId())
                .build();

    }


}
