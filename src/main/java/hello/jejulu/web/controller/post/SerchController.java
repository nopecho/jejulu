package hello.jejulu.web.controller.post;

import hello.jejulu.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class SerchController {

    private final PostService postService;

}