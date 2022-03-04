package hello.jejulu.service.post;

import hello.jejulu.domain.post.Category;
import hello.jejulu.web.dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDto.Detail add(PostDto.Save postSaveDto) throws IOException;
    List<PostDto.Info> selectAllByCategory(Category category);
}
