package hello.jejulu.domain.post;

import hello.jejulu.domain.util.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findTop4ByCategory(Category category, Sort sort);
    Slice<Post> findAllByCategory(Category category, Pageable pageable);
    Page<Post> findAllByHostId(Long hostId, Pageable pageable);
    Page<Post> findByTitleContains(String keyword, Pageable pageable);
    Page<Post> findByHostNameContains(String keyword, Pageable pageable);
    Page<Post> findByCategoryAndTitleContains(Category category, String keyword, Pageable pageable);
    Page<Post> findByCategoryAndHostNameContains(Category category, String keyword, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
}
