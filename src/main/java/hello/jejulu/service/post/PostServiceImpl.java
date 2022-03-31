package hello.jejulu.service.post;

import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.domain.util.Category;
import hello.jejulu.repository.PostRepository_B;
import hello.jejulu.web.controller.post.postDto.PostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl {


    private final PostRepository_B postRepositoryB;
    private final ThumbnailService_B thumbnailServiceB;


    /**
     * 포스트 저장
     * @param form
     * @param host
     * @throws IOException
     */
    @Transactional
    public void savePost(PostSaveDto form, Host host)throws IOException{


        Thumbnail thumbnail = thumbnailServiceB.createThumbnail(form.getFile());
        Post post = PostSaveDto.toPost(form, thumbnail,host);
        postRepositoryB.save(post);

    }

    /**
     * 포스트 조회
     * @param postId
     * @return
     */
    public Post searchPost(Long postId){
        Post post = postRepositoryB.findOne(postId);
        //조회수 증가
        post.countPlus();

        return post;
    }

    /**
     * 포스트 업데이트
     * @param postId
     * @param title
     * @param description
     * @param category
     * @param file
     * @param content
     * @throws IOException
     */
    @Transactional
    public void updatePost(Long postId, String title,
                           String description, Category category,
                           MultipartFile file, String content)throws IOException{
        //포스트 찾기
        Post post = postRepositoryB.findOne(postId);

        //file null 처리


        //썸네일 업데이트
        String updateThumbnailId = thumbnailServiceB.updateThumbnail(file,post.getThumbnail());

        //영속성 썸네일 조회
        Thumbnail thumbnail = thumbnailServiceB.findThumbnail(updateThumbnailId);

        //post 수정
        //dirty check
        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setThumbnail(thumbnail);
        post.setContent(content);
    }

    /**
     * 포스트 삭제
     * @param postId
     */
    @Transactional
    public void deletePost(Long postId){

        Post post = postRepositoryB.findOne(postId);
        Thumbnail thumbnail = thumbnailServiceB.findThumbnail(post.getThumbnail().getId());
        thumbnailServiceB.deleteThumbnail(thumbnail.getId());
        postRepositoryB.removePost(post.getId());

    }

    /**
     * 카데고리별 포스트 조회
     * @param category
     * @return
     */
    public List<Post> findPostCategory(Category category){

        List<Post> posts = postRepositoryB.findByCategory(category);

        return posts;
    }

    /**
     * 카테코리별 포스트 조회 - 버튼 클식시 요청
     * @param category
     * @param offset
     * @return
     */
    public List<Post> findPostCategory_Button(Category category,int offset){

        List<Post> posts = postRepositoryB.findByCategory_Button(category,offset);

        return posts;
    }


    /**
     * 호스트별 포스트 조회
     * @param hostId
     * @return
     */
    public List<Post> findPostHost(Long hostId){
        List<Post> postHost = postRepositoryB.findPostHost(hostId);

        return postHost;
    }

}
