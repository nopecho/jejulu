package hello.jejulu.service.web;

import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto.Info loginByMember(LoginDto loginDto){
        Member loginMember = memberRepository.findByLoginId(loginDto.getLoginId()).orElse(null);
        if(loginMember == null){
            return null;
        }
        if(!passwordEncoder.matches(loginDto.getPassword(),loginMember.getPassword())){
            return null;
        }
        return new MemberDto.Info(loginMember);
    }
}
