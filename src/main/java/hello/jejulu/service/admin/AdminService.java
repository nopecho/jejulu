package hello.jejulu.service.admin;

import hello.jejulu.web.dto.member.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    Page<MemberDto.AdminDetail> getMembersForAdmin(Pageable pageable);
}
