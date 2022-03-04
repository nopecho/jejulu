package hello.jejulu.web.controller.post;

import hello.jejulu.service.post.PostService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/create")
    public String postSaveForm(@ModelAttribute PostDto.Save postSaveDto){
        return "jejulu/posts/post-form";
    }

    @PostMapping
    public String postSave(@ModelAttribute PostDto.Save postSaveDto, Model model) throws IOException {
        PostDto.Detail detail = postService.add(postSaveDto);
        model.addAttribute("detail",detail);
        return "jejulu/posts/post";
    }
}
