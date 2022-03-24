package hello.jejulu.web.controller.post;


import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.repository.HostRepository_B;
import hello.jejulu.repository.ThumbnailRepository_B;
import hello.jejulu.service.host.HostServiceImpl;
import hello.jejulu.service.post.PostServiceImpl;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.controller.post.postForm.PostForm;
import hello.jejulu.web.controller.post.postForm.PostSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostServiceImpl postService;
    private final HostRepository_B hostRepositoryB;
    private final HostServiceImpl hostService;

    //게시물 등록 폼 요청
    @GetMapping("/create")
    public String getPostForm(@ModelAttribute(name="save") PostSaveForm form){

        return "jejulu/posts/post-form";
    }

    //게시물 등록
    @PostMapping
    public String savePost(@Validated@ModelAttribute PostSaveForm form,
                           BindingResult bindingResult,
                           HttpServletRequest request) throws IOException {

        String requestURI = request.getRequestURI();

        if(bindingResult.hasErrors()){
            return "redirect:/posts/create";
        }
        HttpSession session = request.getSession(false);
        Host host =(Host)session.getAttribute(SessionConst.HOST);

        Host entityHost = hostRepositoryB.findByPk(host.getId());


        if(host==null){
            return "redirect:/";
        }else {
            postService.savePost(form,entityHost);
        }

        return"redirect:/";
    }

    //게시물 상세 조회
    @GetMapping("/{postId}")
    public String viewPost(@PathVariable Long postId, Model model,
                           HttpServletRequest request){

        //세션조회-> 호스트가져오기
        HttpSession session = request.getSession(false);
        Host sessionHost = (Host)session.getAttribute(SessionConst.HOST);
        Host entityHost = hostRepositoryB.findByPk(sessionHost.getId());

        //포스트 가져오기
        Post post = postService.searchPost(postId);
        PostForm postView = PostForm.createPostView(post,entityHost);

        model.addAttribute("detail", postView);
        return "jejulu/posts/post";
    }

    //게시물 수정 Form
    @GetMapping("/{postId}/edit")
    public String postEditForm(@PathVariable Long postId,
                               Model model){

        Post post = postService.searchPost(postId);
        model.addAttribute("update", post);

        return "jejulu/posts/post-update-form";

    }

    //게시물 수정
    @PatchMapping("/{postId}")
    public String editForm(@Validated@ModelAttribute PostSaveForm form,
                           BindingResult bindingResult,
                           @PathVariable Long postId) throws IOException {

        if(bindingResult.hasErrors()){
            return "redirect:/posts/{postId}/edit";
        }

        postService.updatePost(postId, form.getTitle(),
                form.getDescription(), form.getCategory(),
                form.getFile(), form.getContent());

        return "redirect:/";
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public String deleteForm(@PathVariable Long postId){

        postService.deletePost(postId);

        return "/";
    }
}
