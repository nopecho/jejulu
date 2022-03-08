package hello.jejulu.web.controller.post;

import hello.jejulu.domain.util.Category;
import hello.jejulu.service.post.PostService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/categorys/{category}")
    public String postsByCategory(@PathVariable Category category, Model model,
                                  @PageableDefault(size = 12, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable){
        Slice<PostDto.Info> postsByCategory = postService.getPostsByCategory(category, pageable);
        model.addAttribute("page",postsByCategory);
        model.addAttribute("category",category);
        return "jejulu/posts/posts";
    }

    @ResponseBody
    @GetMapping("/{category}/load")
    public Slice<PostDto.Info> loadPosts(@PathVariable Category category,
            @PageableDefault(size = 12,sort = "createDate",direction = Sort.Direction.DESC) Pageable pageable){
        return postService.getPostsByCategory(category,pageable);
    }
}
