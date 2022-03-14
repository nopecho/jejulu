package hello.jejulu.web.controller.post;

import hello.jejulu.domain.util.Category;
import hello.jejulu.service.post.PostService;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.dto.PostDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

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
     * @param redirectAttributes
     */
    @PostMapping
    public String postSave(@ModelAttribute PostDto.Save postSaveDto,
                           @SessionAttribute(name = SessionConst.HOST) HostDto.Info loginHost,
                           RedirectAttributes redirectAttributes) throws IOException {
        PostDto.Info savePostInfo = postService.add(postSaveDto, loginHost);
        redirectAttributes.addAttribute("postId",savePostInfo.getId());
        return "redirect:/success/post/{postId}";
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

    /**
     * 카테고리별 게시물 목록 조회 핸들러
     * @param category
     * @param model
     * @param pageable
     */
    @GetMapping("/categorys/{category}")
    public String postsByCategory(@PathVariable Category category, Model model,
                                  @PageableDefault(size = 12, sort = "createDate", direction = Sort.Direction.DESC)Pageable pageable){
        Slice<PostDto.Info> postsByCategory = postService.getPostsByCategory(category, pageable);
        model.addAttribute("page",postsByCategory);
        model.addAttribute("category",category);
        return "jejulu/posts/posts";
    }

    /**
     * 카테고리별 게시물 목록 추가 조회 핸들러
     * @param category
     * @param pageable
     */
    @ResponseBody
    @GetMapping("/{category}/load")
    public Slice<PostDto.Info> loadPosts(@PathVariable Category category,
                                         @PageableDefault(size = 12,sort = "createDate",direction = Sort.Direction.DESC) Pageable pageable){
        return postService.getPostsByCategory(category,pageable);
    }

    /**
     * 호스트가 작성한 게시물 목록 리다이렉트 핸들러
     * @param loginHost
     * @param redirectAttributes
     */
    @GetMapping("/host/info")
    public String lookupHostInfo(@SessionAttribute(name = SessionConst.HOST) HostDto.Info loginHost,
                                 RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("hostId",loginHost.getId());
        return "redirect:/posts/host/{hostId}";
    }

    /**
     * 호스트가 작성한 게시물 목록 조회 핸들러
     * @param hostId
     * @param loginHost
     */
    @ResponseBody
    @GetMapping("/host/{hostId}")
    public List<PostDto.Info> testApi(@PathVariable Long hostId,
                                      @SessionAttribute(name = SessionConst.HOST) HostDto.Info loginHost){
        if(!loginHost.getId().equals(hostId)){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        return postService.getPostsByHost(hostId);
    }

    /**
     * 게시물 수정 폼 요청 핸들러
     * @param postId
     */
    @GetMapping("/{postId}/edit")
    public String postUpdateForm(@PathVariable Long postId,
                                 @SessionAttribute(name = SessionConst.HOST) HostDto.Info loginHost,
                                 Model model){
        if(!postService.isPostByHost(postId,loginHost)){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        PostDto.Detail post = postService.getPostById(postId);
        model.addAttribute("update",post);
        return "jejulu/posts/post-update-form";
    }

    @PatchMapping("/{postId}")
    public String updatePost(@PathVariable Long postId){
        return "redirect:/posts/{postId}";
    }
}
