package hello.jejulu;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.util.Category;
import hello.jejulu.domain.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class InitTest {

    private final PostRepository postRepository;
    private final HostRepository hostRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostConstruct
    public void initPost(){
        Host save = hostRepository.save(Host.builder()
                .loginId("test")
                .password(passwordEncoder.encode("testtt"))
                .role(Role.HOST)
                .email("test@email")
                .phone("01054784056")
                .name("테스트 호스트")
                .addr("대구 광역시 중구 남산동 290")
                .build());

        for(int i = 0; i<100; i++){
            Post post = postRepository.save(Post.builder()
                    .title("TITLE tours" + i)
                    .description("description!!")
                    .thumbnail(null)
                    .content("testContent")
                    .category(Category.TOUR)
                    .host(save)
                    .build());
        }
        for(int i = 0; i<100; i++){
            Post post = postRepository.save(Post.builder()
                    .title("TITLE resturent" + i)
                    .description("description!!")
                    .thumbnail(null)
                    .content("testContent")
                    .category(Category.RESTURENT)
                    .host(save)
                    .build());
        }
        for(int i = 0; i<100; i++){
            Post post = postRepository.save(Post.builder()
                    .title("TITLE hotel" + i)
                    .description("description!!")
                    .thumbnail(null)
                    .content("testContent")
                    .category(Category.HOTEL)
                    .host(save)
                    .build());
        }
    }
}
