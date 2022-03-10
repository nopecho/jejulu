package hello.jejulu.service.host;

import hello.jejulu.web.dto.HostDto;

public interface HostService {
    HostDto.Info add(HostDto.Save hostSaveDto);
    boolean isDuplicateId(String checkId);
}
