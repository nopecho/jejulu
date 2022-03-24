package hello.jejulu;

import hello.jejulu.domain.Role;
import hello.jejulu.domain.host.Host;
import hello.jejulu.repository.HostRepository_B;
import hello.jejulu.service.host.HostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDateHost {

    private final HostRepository_B hostRepositoryB;
    private final HostServiceImpl hostService;

    @PostConstruct
    public void init(){
        hostService.join(new Host(99L,"test_host","111111","보은이","제주특별자치도 제주시 구좌읍 계룡길 5","rnqhdms@gmail.com","010-2202-6380", Role.HOST,null));
    }
}
