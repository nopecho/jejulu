package hello.jejulu.web.dto;

import hello.jejulu.domain.util.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.thumbnail.Thumbnail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Category category;

    @Getter @Setter
    public static class Save{
        private String title;
        private Category category;
        private MultipartFile file;
        private String content;
        private String description;

        public Post toEntity(Thumbnail thumbnail){
            return Post.builder()
                    .title(this.title)
                    .content(this.content)
                    .description(this.description)
                    .thumbnail(thumbnail)
                    .category(this.category)
                    .build();
        }
    }

    @Getter @Setter
    public static class Detail{
        private Long id;
        private String title;
        private String content;
        private String description;
        private String imagePath;
        private Category category;
        private LocalDate createDate;

        public Detail(Post post,String imagePath){
            this.id=post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.description = post.getDescription();
            this.imagePath = imagePath;
            this.category = post.getCategory();
            this.createDate = post.getCreateDate().toLocalDate();
        }
    }

    @Getter @Setter
    public static class Info{
        private Long id;
        private String title;
        private String description;
        private Category category;
        private String imagePath;

        public Info(Post post,String imagePath){
            this.id = post.getId();
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.category = post.getCategory();
            this.imagePath = imagePath;
        }
    }

    @Getter @Setter
    public static class Home{
        private List<PostDto.Info> postsByTour;
        private List<PostDto.Info> postsByResturent;
        private List<PostDto.Info> postsByHotel;
    }
}
