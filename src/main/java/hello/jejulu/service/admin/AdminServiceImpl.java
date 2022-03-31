package hello.jejulu.service.admin;

import hello.jejulu.domain.Role;
import hello.jejulu.domain.admin.Admin;
import hello.jejulu.domain.admin.AdminRepository;
import hello.jejulu.service.member.MemberService;
import hello.jejulu.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final HostRepository hostRepository;
    private final PostRepository postRepository;

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
