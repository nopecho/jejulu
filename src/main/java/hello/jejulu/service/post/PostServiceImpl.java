package hello.jejulu.service.post;

import hello.jejulu.domain.util.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.service.thumbnail.ThumbnailService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ThumbnailService thumbnailService;

    @PostConstruct
    public void initPost(){
        for(int i = 0; i<100; i++){
            postRepository.save( Post.builder()
                    .title("TITLE"+i)
                    .description("description!!")
                    .thumbnail(null)
                    .content("testContent")
                    .category(Category.TOUR)
                    .build());
        }
    }

    @Transactional
    @Override
    public PostDto.Info add(PostDto.Save postSaveDto) throws IOException {
        Thumbnail thumbnail = thumbnailService.add(postSaveDto.getFile());
        Post savePost = postRepository.save(postSaveDto.toEntity(thumbnail));
        if(thumbnail == null){
            return new PostDto.Info(savePost,"");
        }
        return new PostDto.Info(savePost,thumbnail.getPath());
    }

    @Override
    public List<PostDto.Info> getHomePostsByCategory(Category category) {
        Sort sort = sortByCreateDate();
        List<Post> homePosts = postRepository.findTop4ByCategory(category, sort);
        return homePosts.stream()
                .map(post -> post.getThumbnail() == null ? new PostDto.Info(post,"") : new PostDto.Info(post, post.getThumbnail().getPath()))
                .collect(Collectors.toList());
    }

    @Override
    public Slice<PostDto.Info> getPostsByCategory(Category category, Pageable pageable) {
        Slice<Post> findPosts = postRepository.findAllByCategory(category, pageable);
        return findPosts.map(post -> post.getThumbnail() == null ? new PostDto.Info(post,"") : new PostDto.Info(post,post.getThumbnail().getPath()));
    }

    @Transactional
    @Override
    public PostDto.Detail getPostById(Long postId) {
        Post findPost = postRepository.findById(postId).orElse(null);
        if(findPost == null){
            return null;
        }
        findPost.countPlus();
        String imagePath = "";
        if( findPost.getThumbnail() != null){
            imagePath = findPost.getThumbnail().getPath();
        }
        return new PostDto.Detail(findPost,imagePath);
    }

    private Sort sortByCreateDate(){
        return Sort.by(Sort.Direction.DESC,"createDate");
    }
}
