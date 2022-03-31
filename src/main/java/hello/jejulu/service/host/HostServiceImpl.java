package hello.jejulu.service.host;

import hello.jejulu.domain.host.Host;
import hello.jejulu.repository.HostRepository_B;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostServiceImpl{

    private final HostRepository_B hostRepositoryB;

    /**
     * 호스트 회원 가입
     * @param host
     * @return
     */
    @Transactional
    public Host join(Host host){
        validateDuplicateHost(host);
        hostRepositoryB.save(host);
        Host joinedHost = hostRepositoryB.findByPk(host.getId());
        return joinedHost;
    }

    /**
     * 호스트 로그인
     * @param loginId
     * @param password
     * @return
     */
    public Host hostLogin(String loginId,String password){

        return hostRepositoryB.findByLoginId(loginId)
                .filter(h -> h.getPassword().equals(password))
                .orElse(null);

    }

    /**
     * 호스트 정보수정
     * @param hostId
     * @param name
     * @param address
     * @param phone
     * @param email
     * @return
     */
    @Transactional
    public Host updateHost(Long hostId,String name,String address,String phone,String email){
        Host findHost=hostRepositoryB.findByPk(hostId);
        findHost.setHostName(name);
        findHost.setAddress(address);
        findHost.setPhone(phone);
        findHost.setEmail(email);

        return findHost;
    }

    /**
     * 중복 호스트 검증
     * @param host
     */
    private void validateDuplicateHost(Host host){
        List<Host> findHosts = hostRepositoryB.findByName(host.getHostName());
        if(!findHosts.isEmpty()){
            throw new IllegalStateException("이미 존재하는 호스트입니다.");
        }
    }

    /**
     * 호스트 탈퇴
     * @param hostId
     */
    @Transactional
    public void deleteHost(Long hostId){
        hostRepositoryB.removeHost(hostId);
    }

    /**
     * 호스트 조회
     * @param hostId
     * @return
     */
    public Optional<Host> findHostId(String hostId){
        Optional<Host> byLoginId = hostRepositoryB.findByLoginId(hostId);
        return byLoginId;
    }

}
