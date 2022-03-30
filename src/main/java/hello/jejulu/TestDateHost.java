package hello.jejulu;

import hello.jejulu.domain.Role;
import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.post.Category;
import hello.jejulu.service.host.HostServiceImpl;
import hello.jejulu.service.post.PostServiceImpl;
import hello.jejulu.web.controller.post.postDto.PostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TestDateHost {

    private final HostServiceImpl hostService;
    private final PostServiceImpl postService;

   // @Transactional
    //@PostConstruct
    public void init() throws IOException {
//        Host join = hostService.join(new Host(99L, "test_host", "111111", "보은이", "제주특별자치도 제주시 구좌읍 계룡길 5", "rnqhdms@gmail.com", "010-2202-6380", Role.HOST, null));
        Host join = hostService.join(Host.builder()
                .loginId("rhksflwk")
                .password("rhksflwk")
                .hostName("보은이")
                .address("대구 광역시 중구 남산동 290")
                .phone("010-5478-4056")
                .role(Role.HOST)
                .email("rnqhdms@gamil.com")
                .build());

        //        postService.savePost(PostSaveForm.toPost(),);
        postService.savePost(PostSaveDto.builder()
                        .title("title")
                        .description("description")
                        .category(Category.Tour)
                        .content("testcontent")
                .build(), join);
    }


//    @Transactional
//    @PostConstruct
//    public void initPost(){
//        Host byPk = hostRepository_b.findByPk(1L);
////        postService.savePost(PostSaveForm.toPost(),);
//        Post post = Post.builder()
//                .title("title")
//                .description("testtttttttt")
//                .category(Category.Tour)
//                .thumbnail(null)
//                .content("testtest")
//                .count(0)
//                .build();
//        post.setHost(byPk);
//        postRepository.save(post);
//
//    }
}
