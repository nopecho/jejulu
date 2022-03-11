package hello.jejulu.service.host;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    @Override
    public HostDto.Info edit(Long hostId, HostDto.Update updateDto) {
        Optional<Host> findHost = hostRepository.findById(hostId);
        Host host = hostNullCheck(findHost);
        host.updateInfo(updateDto.getName(), updateDto.getPhone(), updateDto.getAddr(), updateDto.getEmail());
        return new HostDto.Info(host);
    }

    @Override
    public boolean remove(Long hostId) {
        Host host = hostNullCheck(hostRepository.findById(hostId));
        hostRepository.delete(host);
        return true;
    }

    @Override
    public boolean isDuplicateId(String checkId) {
        Host findHost = hostRepository.findByLoginId(checkId).orElse(null);
        return findHost != null;
    }

    @Override
    public HostDto.Detail lookupHost(Long hostId) {
        Optional<Host> findHost = hostRepository.findById(hostId);
        Host host = hostNullCheck(findHost);
        return new HostDto.Detail(host);
    }

    private Host hostNullCheck(Optional<Host> findHost){
        Host host = findHost.orElse(null);
        if(host == null){
            throw new CustomException(ErrorCode.HOST_NOT_FOUND);
        }
        return host;
    }
}
