package hello.jejulu.web.controller;

import hello.jejulu.domain.util.Category;
import hello.jejulu.service.post.PostServiceImpl;
import hello.jejulu.web.dto.post.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
public class HomeController {

    private final PostServiceImpl postService;

    @GetMapping("/")
    public String home(@ModelAttribute PostDto.Home homePosts){
//        homePosts.setPostsByTour(postService.getHomePostsByCategory(Category.TOUR));
//        homePosts.setPostsByResturent(postService.getHomePostsByCategory(Category.RESTURENT));
//        homePosts.setPostsByHotel(postService.getHomePostsByCategory(Category.HOTEL));
        return "jejulu/home";
    }
}
