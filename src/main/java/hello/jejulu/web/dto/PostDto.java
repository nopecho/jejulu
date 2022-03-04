package hello.jejulu.web.dto;

import hello.jejulu.domain.post.Category;
import hello.jejulu.domain.post.Post;
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

        public Post toEntity(){
            return Post.builder()
                    .title(this.title)
                    .content(this.content)
                    .category(this.category)
                    .build();
        }
    }

    @Getter @Setter
    public static class Detail{
        private Long id;
        private String title;
        private String content;
        private Category category;
        private String imagePath;
        private LocalDate createDate;

        public Detail(Post post,String imagePath){
            this.id=post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
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
        private String imagePath;

        public Info(Post post,String imagePath){
            this.id = post.getId();
            this.title = post.getTitle();
            this.description = post.getContent();
            this.imagePath = imagePath;
        }
    }

    @Getter @Setter
    public static class Home{
        private List<PostDto.Info> postsByTour;
    }

//    @Getter @Setter
//    public static class Update{
//
//    }
}
