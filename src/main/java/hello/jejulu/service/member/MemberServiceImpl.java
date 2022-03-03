package hello.jejulu.service.member;

import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Transactional
    @Override
    public MemberDto.Info edit(Long memberId,MemberDto.Update memberUpdateDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        Member member = findMember.orElse(null);
        member.updateInfo(memberUpdateDto.getName(),memberUpdateDto.getPhone(),memberUpdateDto.getEmail());
        return new MemberDto.Info(member);
    }

    @Override
    public boolean isDuplicateId(String checkedId){
        Member findMember = memberRepository.findByLoginId(checkedId).orElse(null);
        if(findMember == null){
            return false;
        }
        return true;
    }

    @Override
    public MemberDto lookupMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        return new MemberDto(findMember);
    }

    @Override
    public List<MemberDto> selectAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }
}
