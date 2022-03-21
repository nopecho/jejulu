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

    //호스트 회원 가입
    @Transactional
    public Host join(Host host){
        validateDuplicateHost(host);
        hostRepositoryB.save(host);
        Host joinedHost = hostRepositoryB.findByPk(host.getId());
        return joinedHost;
    }

    //호스트 로그인
    public Host hostLogin(String loginId,String password){

        return hostRepositoryB.findByLoginId(loginId)
                .filter(h -> h.getPassword().equals(password))
                .orElse(null);

    }

    //호스트 정보수정
    @Transactional
    public Host updateHost(Long hostId,String name,String address,String phone,String email){
        Host findHost=hostRepositoryB.findByPk(hostId);
        findHost.setHostName(name);
        findHost.setAddress(address);
        findHost.setPhone(phone);
        findHost.setEmail(email);

        return findHost;
    }

    //중복 호스트 검증
    private void validateDuplicateHost(Host host){
        List<Host> findHosts = hostRepositoryB.findByName(host.getHostName());
        if(!findHosts.isEmpty()){
            throw new IllegalStateException("이미 존재하는 호스트입니다.");
        }
    }


}
