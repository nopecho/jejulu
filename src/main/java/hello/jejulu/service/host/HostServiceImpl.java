package hello.jejulu.service.host;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.web.dto.HostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class HostServiceImpl implements HostService{

    private final HostRepository hostRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public HostDto.Info add(HostDto.Save hostSaveDto) {
        Host saveHost = hostRepository.save(hostSaveDto.toEntity(passwordEncoder));
        return new HostDto.Info(saveHost);
    }

    @Override
    public boolean isDuplicateId(String checkId) {
        Host findHost = hostRepository.findByLoginId(checkId).orElse(null);
        if(findHost == null){
            return false;
        }
        return true;
    }
}
