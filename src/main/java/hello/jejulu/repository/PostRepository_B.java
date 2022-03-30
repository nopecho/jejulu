package hello.jejulu.repository;

import hello.jejulu.domain.post.Category;
import hello.jejulu.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository_B {

    private final EntityManager em;


    //포스트 저장
    public void save(Post post) {

        if (post.getId()==null) {
            em.persist(post);
        }else{
            em.merge(post);
        }
    }

    //포스트 조회
    public Post findOne (Long id){
        return em.find(Post.class,id);
    }


//    //아이템 조회
//    public Post findByPk(Long id){
//        return findAll().stream()
//                .filter(p->p.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//
//    }



    //포스트 모두 조회
    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //카테고리별 포스트 조회
    public List<Post> findByCategory(Category category){
        return em.createQuery("select p from Post p where p.category=:category",Post.class)
                .setParameter("category",category)
                .setFirstResult(0)
                .setMaxResults(12)
                .getResultList();

    }

    // 포스트 삭제
    public void removePost(Long postId){
        Post post = findOne(postId);
        em.remove(post);
    }
}
