package hello.jejulu.web.controller.post.postDto;

import hello.jejulu.domain.util.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class postCategoryDto {

    private Long id; //포스트아이디
    private String path;
    private String title;
    private String description;
    private String writer; //호스트 이름
    private int count;
    private Category category;
    private LocalDate createDate;

    public postCategoryDto(Long id, String path, String title, String description, String writer, int count, Category category, LocalDate createDate) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.count = count;
        this.category = category;
        this.createDate = createDate;
    }
}
