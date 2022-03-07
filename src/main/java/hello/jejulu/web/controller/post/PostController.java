package hello.jejulu.web.controller.post;

import hello.jejulu.domain.post.Category;
import hello.jejulu.service.post.PostService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 작성 폼 요청 핸들러
     * @param postSaveDto
     */
    @GetMapping("/create")
    public String postSaveForm(@ModelAttribute PostDto.Save postSaveDto){
        return "jejulu/posts/post-form";
    }

    /**
     * 게시물 작성 핸들러
     * @param postSaveDto
     * @param model
     */
    @PostMapping
    public String postSave(@ModelAttribute PostDto.Save postSaveDto, Model model) throws IOException {
        PostDto.Info savePostInfo = postService.add(postSaveDto);
        model.addAttribute("info",savePostInfo);
        return "jejulu/success/success-post";
    }

    /**
     * 게시물 상세 조회 핸들러
     * @param postId
     * @param model
     */
    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model){
        PostDto.Detail post = postService.getPostById(postId);
        model.addAttribute("detail",post);
        return "jejulu/posts/post";
    }

    @GetMapping("/category")
    public String postsByCategory(@RequestParam(name = "c") Category category, Model model){
        Slice<PostDto.Info> postsByCategory = postService.getPostsByCategory(category);
        model.addAttribute("page",postsByCategory);
        model.addAttribute("category",category);
        return "jejulu/posts/posts";
    }
}
