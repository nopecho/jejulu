package hello.jejulu.web.controller.post.postDto;

import lombok.Data;

@Data
public class postCategoryDto {

    private Long id; //포스트아이디
    private String path;
    private String title;
    private String description;
    private String writer; //호스트 이름
    private int count;

    public postCategoryDto(Long id,String path, String title, String description, String writer, int count) {

        this.id=id;
        this.path = path;
        this.title = title;
        this.description = description;
        this.writer = writer;
        this.count = count;
    }
}
