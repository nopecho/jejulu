package hello.jejulu.service.post;

import hello.jejulu.domain.post.Category;
import hello.jejulu.web.dto.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDto.Info add(PostDto.Save postSaveDto) throws IOException;
    PostDto.Detail getPostById(Long postId);
    List<PostDto.Info> getHomePostsByCategory(Category category);
    Slice<PostDto.Info> getPostsByCategory(Category category, Pageable pageable);


}
