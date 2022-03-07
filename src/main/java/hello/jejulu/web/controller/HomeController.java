package hello.jejulu.web.controller;

import hello.jejulu.domain.post.Category;
import hello.jejulu.service.post.PostService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@ModelAttribute PostDto.Home homePosts){
        homePosts.setPostsByTour(postService.getHomePostsByCategory(Category.Tour));
        homePosts.setPostsByResturent(postService.getHomePostsByCategory(Category.Resturent));
        homePosts.setPostsByHotel(postService.getHomePostsByCategory(Category.Hotel));
        return "jejulu/home";
    }

    @GetMapping("/sign-up")
    public String signUpSelect(){
        return "jejulu/sign/sign-select";
    }
}
