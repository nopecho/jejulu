package hello.jejulu.web.controller.post;


import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.repository.HostRepository_B;
import hello.jejulu.repository.ThumbnailRepository_B;
import hello.jejulu.service.post.PostServiceImpl;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.controller.post.postForm.PostSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/create")
    public String getPostForm(@ModelAttribute(name="save") PostSaveForm form){

        return "jejulu/posts/post-form";
    }

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

        Host targetHost = hostRepositoryB.findByPk(host.getId());


        if(host==null){
            return "redirect:/";
        }else {
            postService.savePost(form,targetHost);
        }

        return"redirect:/";
    }
}
