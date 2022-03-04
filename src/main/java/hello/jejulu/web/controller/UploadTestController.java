package hello.jejulu.web.controller;

import com.google.firebase.auth.FirebaseAuthException;
import hello.jejulu.service.web.FirebaseService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UploadTestController {

    private final FirebaseService firebaseService;

    @GetMapping("/upload")
    public String uploadForm(@ModelAttribute PostDto.Save postSaveDto){
        return "jejulu/posts/post-form";
    }

    @PostMapping("/upload")
    public String uploadPost(@ModelAttribute PostDto.Save postSaveDto) throws IOException, FirebaseAuthException {
        log.info("post/ title = {}, category = {}, thmbnail = {}, content = {}",postSaveDto.getTitle(),postSaveDto.getCategory(), postSaveDto.getThumbnail().getOriginalFilename(), postSaveDto.getContent());

        String thumbnailPath = firebaseService.uploadImage(postSaveDto.getThumbnail());
        log.info("이미지 링크 = {}",thumbnailPath);
        return "jejulu/home";
    }
}
