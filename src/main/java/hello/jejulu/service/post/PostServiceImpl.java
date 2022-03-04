package hello.jejulu.service.post;

import hello.jejulu.domain.post.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.service.thumbnail.ThumbnailService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ThumbnailService thumbnailService;

    @Transactional
    @Override
    public PostDto.Detail add(PostDto.Save postSaveDto) throws IOException {
        Thumbnail thumbnail = thumbnailService.add(postSaveDto.getFile());
        Post savePost = postRepository.save(Post.builder()
                .title(postSaveDto.getTitle())
                .content(postSaveDto.getContent())
                .thumbnail(thumbnail)
                .category(Category.Tour)
                .build());
        return new PostDto.Detail(savePost);
    }

    @Override
    public List<PostDto.Info> selectAllByCategory(Category category) {
        List<Post> posts = postRepository.findAllByCategory(category);
        return posts.stream()
                .map(PostDto.Info::new)
                .collect(Collectors.toList());
    }
}
