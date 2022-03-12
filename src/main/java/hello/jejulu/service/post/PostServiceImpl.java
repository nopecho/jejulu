package hello.jejulu.service.post;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.domain.util.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.service.thumbnail.ThumbnailService;
import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.dto.PostDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final HostRepository hostRepository;
    private final ThumbnailService thumbnailService;

    @Transactional
    @Override
    public PostDto.Info add(PostDto.Save postSaveDto, HostDto.Info loginHost) throws IOException {
        Host writerHost = hostRepository.getById(loginHost.getId());
        Thumbnail thumbnail = thumbnailService.add(postSaveDto.getFile());
        Post savePost = postRepository.save(postSaveDto.toEntity(thumbnail, writerHost));
        writerHost.addPosts(savePost);
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

    @Override
    public List<PostDto.Info> getPostsByHost(Long hostId) {
        Host host = hostRepository.findById(hostId).orElse(null);
        if (host == null) {
            throw new CustomException(ErrorCode.HOST_NOT_FOUND);
        }
        return host.getPosts().stream()
                .map(post -> post.getThumbnail() == null ? new PostDto.Info(post,"") : new PostDto.Info(post, post.getThumbnail().getPath()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostDto.Detail getPostById(Long postId) {
        Post findPost = postRepository.findById(postId).orElse(null);
        if(findPost == null){
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
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
