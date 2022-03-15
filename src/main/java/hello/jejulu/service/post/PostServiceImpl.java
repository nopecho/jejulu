package hello.jejulu.service.post;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.host.HostRepository;
import hello.jejulu.domain.util.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.service.thumbnail.ThumbnailService;
import hello.jejulu.service.util.ServiceUtil;
import hello.jejulu.web.dto.HostDto;
import hello.jejulu.web.dto.PostDto;
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
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final HostRepository hostRepository;
    private final ThumbnailService thumbnailService;

    @Transactional
    @Override
    public PostDto.Info add(PostDto.Save postSaveDto, HostDto.Info loginHost) throws IOException {
        Host writerHost = hostRepository.getById(loginHost.getId());
        Thumbnail thumbnail = thumbnailService.create(postSaveDto.getFile());
        Post savePost = postRepository.save(postSaveDto.toEntity(thumbnail, writerHost));
        return new PostDto.Info(savePost, ServiceUtil.extractedPath(thumbnail));
    }

    @Transactional
    @Override
    public void edit(Long postId, PostDto.Update postUpdateDto) throws IOException {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        Thumbnail thumbnail = thumbnailService.update(postUpdateDto.getFile(), post.getThumbnail());
        post.updateInfo(postUpdateDto.getTitle(),
                postUpdateDto.getDescription(),
                postUpdateDto.getCategory(),
                postUpdateDto.getContent(),
                thumbnail);
    }

    @Override
    public List<PostDto.Info> getHomePostsByCategory(Category category) {
        List<Post> homePosts = postRepository.findTop4ByCategory(category, sortByCreateDate());
        return homePosts.stream()
                .map(post -> new PostDto.Info(post, ServiceUtil.extractedPath(post.getThumbnail())))
                .collect(Collectors.toList());
    }

    @Override
    public Slice<PostDto.Info> getPostsByCategory(Category category, Pageable pageable) {
        Slice<Post> findPosts = postRepository.findAllByCategory(category, pageable);
        return findPosts.map(post -> new PostDto.Info(post, ServiceUtil.extractedPath(post.getThumbnail())));
    }

    @Override
    public List<PostDto.Info> getPostsByHost(Long hostId) {
        Host host = ServiceUtil.getEntityByNullCheck(hostRepository.findById(hostId));
        return host.getPosts().stream()
                .map(post -> new PostDto.Info(post, ServiceUtil.extractedPath(post.getThumbnail())))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long postId) {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        postRepository.delete(post);
    }

    @Transactional
    @Override
    public PostDto.Detail getPostById(Long postId) {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        post.countPlus();
        String imagePath = ServiceUtil.extractedPath(post.getThumbnail());
        return new PostDto.Detail(post, imagePath, post.getHost());
    }

    @Override
    public PostDto.Detail getUpdatePostById(Long postId) {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        String imagePath = ServiceUtil.extractedPath(post.getThumbnail());
        return new PostDto.Detail(post, imagePath, post.getHost());
    }

    @Override
    public boolean isPostByHost(Long postId, HostDto.Info loginHost) {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        Host host = post.getHost();
        return host.getId().equals(loginHost.getId());
    }

    private Sort sortByCreateDate() {
        return Sort.by(Sort.Direction.DESC, "createDate");
    }
}
