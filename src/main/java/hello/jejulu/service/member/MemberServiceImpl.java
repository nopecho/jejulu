package hello.jejulu.service.member;

import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public MemberDto.Save add(MemberDto.Save memberSaveDto) {
        memberRepository.save(memberSaveDto.toEntity(passwordEncoder));
        return memberSaveDto;
    }

    @Override
    public boolean isDuplicateId(String checkedId){
        Member findMember = memberRepository.findByLoginId(checkedId).orElse(null);
        if(findMember == null){
            return false;
        }
        return true;
    }
}
