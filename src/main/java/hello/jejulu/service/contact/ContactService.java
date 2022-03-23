package hello.jejulu.service.contact;

import hello.jejulu.domain.contact.ContactRepository;
import hello.jejulu.domain.util.Role;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.contact.ContactDto;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Transactional
    public void createContact(ContactDto contactDto, HttpSession session){
        Role role = resolveLoginRole(session);
        String loginId = resolveLoginId(session);
        contactRepository.save(contactDto.toEntity(role, loginId));
    }

    private Role resolveLoginRole(HttpSession session){
        if (session == null){
            return Role.GUEST;
        }
        if (session.getAttribute(SessionConst.HOST) != null){
            return Role.HOST;
        }
        if (session.getAttribute(SessionConst.MEMBER) != null){
            return Role.MEMBER;
        }
        return Role.GUEST;
    }

    private String resolveLoginId(HttpSession session){
        if (session == null){
            return "GUEST";
        }
        if (session.getAttribute(SessionConst.HOST) instanceof HostDto.Info){
            return ((HostDto.Info) session.getAttribute(SessionConst.HOST)).getLoginId();
        }
        if (session.getAttribute(SessionConst.MEMBER) instanceof MemberDto.Info) {
            return ((MemberDto.Info) session.getAttribute(SessionConst.MEMBER)).getLoginId();
        }
        return "GUEST";
    }
}
