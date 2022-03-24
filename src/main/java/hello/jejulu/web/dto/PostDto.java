package hello.jejulu.web.dto;

import hello.jejulu.domain.post.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class PostDto {

    @Getter @Setter
    public static class Save{
        private String title;
        private Category category;
        private MultipartFile thumbnail;
        private String content;
    }
}
