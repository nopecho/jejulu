package hello.jejulu.web.dto;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.util.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.thumbnail.Thumbnail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class PostDto {

    @Getter @Setter
    public static class Save{
        private String title;
        private Category category;
        private MultipartFile file;
        private String content;
        private String description;

        public Post toEntity(Thumbnail thumbnail, Host host){
            return Post.builder()
                    .title(this.title)
                    .content(this.content)
                    .description(this.description)
                    .count(0)
                    .thumbnail(thumbnail)
                    .category(this.category)
                    .host(host)
                    .build();
        }
    }

    @Getter @Setter
    public static class Detail{
        private Long id;
        private String title;
        private String content;
        private String description;
        private int count;
        private String imagePath;
        private Category category;
        private LocalDate createDate;
        private String hostName;
        private String hostPhone;
        private String hostEmail;
        private String hostAddr;

        public Detail(Post post,String imagePath, Host host){
            this.id=post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.description = post.getDescription();
            this.count = post.getCount();
            this.imagePath = imagePath;
            this.category = post.getCategory();
            this.createDate = post.getCreateDate().toLocalDate();
            this.hostName = host.getName();
            this.hostPhone = host.getPhone();
            this.hostEmail = host.getEmail();
            this.hostAddr = host.getAddr();
        }
    }

    @Getter @Setter
    public static class Info{
        private Long id;
        private String title;
        private String description;
        private int count;
        private Category category;
        private String imagePath;
        private String writer;

        public Info(Post post,String imagePath){
            this.id = post.getId();
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.count=post.getCount();
            this.category = post.getCategory();
            this.imagePath = imagePath;
            this.writer = post.getHost().getName();
        }
    }

    @Getter @Setter
    public static class Home{
        private List<PostDto.Info> postsByTour;
        private List<PostDto.Info> postsByResturent;
        private List<PostDto.Info> postsByHotel;
    }
}
