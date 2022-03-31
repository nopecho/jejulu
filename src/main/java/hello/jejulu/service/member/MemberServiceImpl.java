package hello.jejulu.service.member;

import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.service.util.ServiceUtil;
import hello.jejulu.web.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public MemberDto.Detail getMemberById(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        Member member = ServiceUtil.getEntityByNullCheck(findMember);
        return new MemberDto.Detail(member);
    }

    @Override
    public boolean isDuplicateId(String checkedId){
        Member findMember = memberRepository.findByLoginId(checkedId).orElse(null);
        return findMember != null;
    }

    @Transactional
    @Override
    public MemberDto.Info add(MemberDto.Save memberSaveDto) {
        Member saveMember = memberRepository.save(memberSaveDto.toEntity(passwordEncoder));
        return new MemberDto.Info(saveMember);
    }

    @Transactional
    @Override
    public MemberDto.Info edit(Long memberId,MemberDto.Update memberUpdateDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        Member member = ServiceUtil.getEntityByNullCheck(findMember);
        member.updateInfo(memberUpdateDto.getName(),memberUpdateDto.getPhone(),memberUpdateDto.getEmail());
        return new MemberDto.Info(member);
    }

    @Transactional
    @Override
    public boolean remove(Long memberId) {
        Member member = ServiceUtil.getEntityByNullCheck(memberRepository.findById(memberId));
        if(!member.getBookings().isEmpty()){
            return false;
        }
        memberRepository.deleteById(member.getId());
        return true;
    }
}
