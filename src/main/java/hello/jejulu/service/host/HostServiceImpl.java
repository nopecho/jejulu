package hello.jejulu.service.host;

import hello.jejulu.domain.host.Host;
import hello.jejulu.repository.HostRepository_B;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostServiceImpl implements HostService{

    private final HostRepository_B hostRepositoryB;

    //회원 가입
    @Transactional
    public Long join(Host host){
        validateDuplicateHost(host);
        hostRepositoryB.save(host);
        return host.getId();
    }

    //중복 호스트 검증
    private void validateDuplicateHost(Host host){
        List<Host> findHosts = hostRepositoryB.findByName(host.getHostName());
        if(!findHosts.isEmpty()){
            throw new IllegalStateException("이미 존재하는 호스트입니다.");
        }
    }


}
