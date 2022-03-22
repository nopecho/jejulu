package hello.jejulu.service.admin;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.util.Role;
import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.dto.member.MemberDto;
import hello.jejulu.web.dto.post.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final HostRepository hostRepository;
    private final PostRepository postRepository;

    @PostConstruct
    @Transactional
    public void initAdmin(){
        Admin admin = Admin.builder()
                .loginId("admin")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();
        adminRepository.save(admin);
    }

    @Override
    public Page<MemberDto.AdminDetail> getMembersForAdmin(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberDto.AdminDetail::new);
    }

    @Override
    public Page<HostDto.AdminDetail> getHostsForAdmin(Pageable pageable) {
        Page<Host> hosts = hostRepository.findAll(pageable);
        return hosts.map(HostDto.AdminDetail::new);
    }

    @Override
    public Page<PostDto.AdminDetail> getPostsForAdmin(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostDto.AdminDetail::new);
    }
}
