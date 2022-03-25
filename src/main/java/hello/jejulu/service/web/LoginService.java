package hello.jejulu.service.web;

import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.dto.member.MemberDto;
import hello.jejulu.web.dto.login.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final HostRepository hostRepository;

    public MemberDto.Info loginByMember(LoginDto loginDto){
        Member loginMember = memberRepository.findByLoginId(loginDto.getLoginId()).orElse(null);
        if(!isLogin(loginMember, loginDto.getPassword())){
            return null;
        }
        return new MemberDto.Info(loginMember);
    }

    public HostDto.Info loginByHost(LoginDto loginDto){
        Host loginHost = hostRepository.findByLoginId(loginDto.getLoginId()).orElse(null);
        if(!isLogin(loginHost, loginDto.getPassword())){
            return null;
        }
        return new HostDto.Info(loginHost);
    }

    public Admin loginByAdmin(LoginDto loginDto){
        Admin admin = adminRepository.findByLoginId(loginDto.getLoginId()).orElse(null);
        if(!isLogin(admin,loginDto.getPassword())){
            return null;
        }
        return admin;
    }

    private boolean isLogin(Object target, String rawPassword){
        if(target == null){
            return false;
        }
        if(target instanceof Member){
            return passwordEncoder.matches(rawPassword,((Member) target).getPassword());
        }
        if(target instanceof Host){
            return passwordEncoder.matches(rawPassword, ((Host) target).getPassword());
        }
        if(target instanceof Admin){
            return ((Admin) target).getPassword().equals(rawPassword);
        }
        return false;
    }
}
