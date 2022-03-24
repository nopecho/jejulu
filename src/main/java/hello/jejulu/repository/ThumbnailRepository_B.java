package hello.jejulu.repository;

import hello.jejulu.domain.thumbnail.Thumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;



@Repository
@RequiredArgsConstructor

public class ThumbnailRepository_B {

    private final EntityManager em;

    //썸네일 저장
    public String save(Thumbnail thumbnail) {
        if (thumbnail.getId() == null) {
            em.persist(thumbnail);
        } else {
            em.merge(thumbnail);
        }

        return thumbnail.getId();
    }

    //썸네일 조회
    public Thumbnail find(String id) {
        return em.find(Thumbnail.class, id);
    }


    //썸네일 삭제
    public void delete(String id) {
        Thumbnail thumbnail = find(id);
        em.remove(thumbnail);

    }


//    //썸네일 업데이트
//    public String updateThumbnail(MultipartFile file, Thumbnail thumbnail) throws IOException {
//
//        if (file.isEmpty()) {
//            return thumbnail.getId();
//        }
//        // Thumbnail findThumbnail = findThumbnail(thumbnail.getId());
////        Thumbnail savedThumbnail = createThumbnail(file);
//        thumbnail.updateEntityThumbnail(uploadImage(file), file.getOriginalFilename());
////        String thumbnailId = save(savedThumbnail);
//
//        return thumbnail.getId();
//
//
//    }


}

