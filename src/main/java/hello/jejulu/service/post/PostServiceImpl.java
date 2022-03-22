package hello.jejulu.service.post;

import hello.jejulu.domain.host.Host;
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
}
