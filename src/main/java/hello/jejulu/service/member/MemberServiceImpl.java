package hello.jejulu.service.member;

import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

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
        Member member = getMemberByNullCheck(findMember);
        member.updateInfo(memberUpdateDto.getName(),memberUpdateDto.getPhone(),memberUpdateDto.getEmail());
        return new MemberDto.Info(member);
    }

    @Transactional
    @Override
    public boolean remove(Long memberId) {
        Member member = getMemberByNullCheck(memberRepository.findById(memberId));
        memberRepository.deleteById(member.getId());
        return true;
    }

    @Override
    public boolean isDuplicateId(String checkedId){
        Member findMember = memberRepository.findByLoginId(checkedId).orElse(null);
        return findMember != null;
    }

    @Override
    public MemberDto.Detail getMemberById(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        Member member = getMemberByNullCheck(findMember);
        return new MemberDto.Detail(member);
    }

    @Override
    public List<MemberDto> selectAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }

    private Member getMemberByNullCheck(Optional<Member> findMember){
        Member member = findMember.orElse(null);
        if(member == null){
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }
}
