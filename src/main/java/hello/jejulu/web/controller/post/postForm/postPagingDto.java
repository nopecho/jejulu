package hello.jejulu.web.controller.post.postForm;

import hello.jejulu.domain.post.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class postPagingDto {

    private List<postCategoryDto> content;

    private int totalPages;

    public int getTotalPages(List<Post> postList) {
        return Math.round(postList.size()/12)+1;

    }


    public postPagingDto(List<Post> postList) {
        this.content=postList.stream()
                .map(p -> new postCategoryDto(p.getId(),p.getThumbnail().getPath(),p.getTitle(),
                        p.getDescription(),p.getHost().getHostName(),p.getCount()))
                .collect(Collectors.toList());
        this.totalPages=getTotalPages(postList);

    }
}
