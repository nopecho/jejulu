package hello.jejulu.service.admin;

import hello.jejulu.web.dto.MemberDto;
import java.util.List;

public interface AdminService {
    List<MemberDto> findMembers();
}
