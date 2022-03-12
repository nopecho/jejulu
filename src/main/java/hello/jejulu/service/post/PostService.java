package hello.jejulu.service.post;

import hello.jejulu.domain.util.Category;
import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.dto.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDto.Info add(PostDto.Save postSaveDto, HostDto.Info loginHost) throws IOException;
    PostDto.Detail getPostById(Long postId);
    List<PostDto.Info> getHomePostsByCategory(Category category);
    Slice<PostDto.Info> getPostsByCategory(Category category, Pageable pageable);
    List<PostDto.Info> getPostsByHost(Long hostId);
}
