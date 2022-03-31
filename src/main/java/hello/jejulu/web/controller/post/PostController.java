package hello.jejulu.web.controller.post;


import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.post.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.repository.HostRepository_B;
import hello.jejulu.service.post.PostServiceImpl;
import hello.jejulu.service.post.ThumbnailService_B;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.controller.post.postDto.PostDto;
import hello.jejulu.web.controller.post.postDto.PostSaveDto;
import hello.jejulu.web.controller.post.postDto.postPagingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController implements SessionConst {

    private final PostServiceImpl postService;
    private final HostRepository_B hostRepositoryB;
    private final ThumbnailService_B thumbnailServiceB;

    /**
     * //게시물 등록 폼 요청
     * @param form
     * @return
     */
    @GetMapping("/create")
    public String getPostForm(@ModelAttribute(name="save") PostSaveDto form){

        return "jejulu/posts/post-form";
    }


    /**
     * 게시물 등록
     */
    @PostMapping
    public String savePost(@Validated@ModelAttribute PostSaveDto form,
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
        }
       // Thumbnail thumbnail = thumbnailServiceB.createThumbnail(form.getFile());
       // Thumbnail entityThumbnail = thumbnailServiceB.findThumbnail(thumbnailId);
        postService.savePost(form,entityHost);

        return"redirect:/";
    }

    /**
     * 게시물 상세 조회
     */
    @GetMapping("/{postId}")
    public String viewPost(@PathVariable Long postId, Model model,
                           HttpServletRequest request){

        //세션조회-> 호스트가져오기
        HttpSession session = request.getSession(false);
        Host sessionHost = (Host)session.getAttribute(SessionConst.HOST);
        Host entityHost = hostRepositoryB.findByPk(sessionHost.getId());

        //포스트 가져오기
        Post post = postService.searchPost(postId);
        PostDto postView = PostDto.createPostView(post,entityHost);

        model.addAttribute("detail", postView);
        return "jejulu/posts/post";
    }


    /**
     *  게시물 수정 Form
     */
    @GetMapping("/{postId}/edit")
    public String postEditForm(@PathVariable Long postId,
                               Model model){

        Post post = postService.searchPost(postId);
        model.addAttribute("update", post);

        return "jejulu/posts/post-update-form";

    }

    /**
    *게시물 수정
     */
    @PatchMapping("/{postId}")
    public String editForm(@Validated@ModelAttribute PostSaveDto form,
                           BindingResult bindingResult,
                           @PathVariable Long postId) throws IOException {

        if(bindingResult.hasErrors()){
            return "redirect:/posts/{postId}/edit";
        }

        postService.updatePost(postId, form.getTitle(),
                form.getDescription(), form.getCategory(),
                form.getFile(), form.getContent());

        return "redirect:/posts/{postId}";
    }

    /**
     * 게시물 삭제
     */

    @DeleteMapping("/{postId}")
    public String deleteForm(@PathVariable Long postId){

        postService.deletePost(postId);

        return "redirect:/";
    }


    /**
     * 카테고리별 게시물 조회
     * @param category
     * @param model
     * @return
     */
    @GetMapping("/categorys/{category}")
    public String viewPostCategory(@PathVariable Category category,Model model){
        log.info("category={}",category);

        //Entity 조회
        List<Post> postList = postService.findPostCategory(category);

        //Entity -> DTO로 변환
        postPagingDto dto = new postPagingDto(postList);


        model.addAttribute("page",dto);
        model.addAttribute("category",category);

        return "jejulu/posts/posts";
    }


    /**
     * 카테고리별 게시물 조회 더보기버튼 요청
     * JSON으로 응답 - Single Page Application 형식 (비동기요청)으로 내려줌
     */
    @ResponseBody
    @GetMapping("/{category}/load")
    public postPagingDto buttonCategory(@PathVariable Category category,
                                 @RequestParam(name = "countClick") int offset){
        // Entity 조회
        List<Post> postCategory = postService.findPostCategory_Button(category,offset);

        //DTO 변환 (12개)
        postPagingDto postPagingDto = new postPagingDto(postCategory);

        return postPagingDto;

    }


    /**
     * 호스트 게시물 조회
     */
    @GetMapping("/hosts/{hostId}")
    public String postOfHost(@PathVariable Long hostId,Model model){

        //Entity 조회
        List<Post> postHost = postService.findPostHost(hostId);

        //DTO 변환
        postPagingDto postPagingDto = new postPagingDto(postHost);

        model.addAttribute("page",postPagingDto);

        return "jejulu/posts/posts-host";

    }
}
