package hello.jejulu.web.controller.post.postForm;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.post.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.repository.ThumbnailRepository_B;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter@Setter
@RequiredArgsConstructor

public class PostSaveForm {

    private final ThumbnailRepository_B thumbnailRepository_B;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private Category category;


    private MultipartFile file;

    @NotBlank
    private String content;


    public static Post toPost(PostSaveForm form,Thumbnail thumbnail,Host host){
        Post post= Post.builder()
                .title(form.getTitle())
                .description(form.getDescription())
                .category(form.getCategory())
                .thumbnail(thumbnail)
                .content(form.getContent())
                .count(0)
                .build();

        post.setHost(host);
        return post;

    }
}
