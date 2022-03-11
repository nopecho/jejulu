package hello.jejulu.service.host;

import hello.jejulu.web.dto.HostDto;

public interface HostService {
    HostDto.Detail lookupHost(Long hostId);
    boolean isDuplicateId(String checkId);
    HostDto.Info add(HostDto.Save hostSaveDto);
    HostDto.Info edit(Long hostId, HostDto.Update hostUpdateDto);
    boolean remove(Long hostId);
}
