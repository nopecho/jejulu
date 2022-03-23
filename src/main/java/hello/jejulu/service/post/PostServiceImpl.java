package hello.jejulu.service.post;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.post.Category;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.repository.PostRepository_B;
import hello.jejulu.repository.ThumbnailRepository_B;
import hello.jejulu.web.controller.post.postForm.PostSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final ThumbnailRepository_B thumbnailRepository_B;
    private final PostRepository_B postRepositoryB;




    //포스트 저장
    @Transactional
    public void savePost(PostSaveForm form, Host host)throws IOException {

        Thumbnail thumbnail = thumbnailRepository_B.saveThumbnail(form.getFile());
        Post post = PostSaveForm.toPost(form, thumbnail,host);
        postRepositoryB.save(post);

    }

    //포스트 조회
    public Post searchPost(Long postId){
        Post post = postRepositoryB.findOne(postId);

        return post;
    }

    //포스트 업데이트
    @Transactional
    public void updatePost(Long postId, String title,
                           String description,Category category,
                           MultipartFile file,String content)throws IOException{

        //썸네일 저장
        Thumbnail thumbnail = thumbnailRepository_B.saveThumbnail(file);
        Thumbnail entityThumbnail = thumbnailRepository_B.findThumbnail(thumbnail.getId());
        //post 수정
        Post post = postRepositoryB.findOne(postId);
        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setThumbnail(entityThumbnail);
        post.setContent(content);
    }
}
