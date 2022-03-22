package hello.jejulu.repository;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import hello.jejulu.domain.thumbnail.Thumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThumbnailRepository_B {

    private final EntityManager em;

    //썸네일 저장
    @Transactional
    public String save(Thumbnail thumbnail){
        if(thumbnail.getId()==null){
            em.persist(thumbnail);
        }else{
            em.merge(thumbnail);
        }
        return thumbnail.getId();
    }

    //썸네일 조회
    public Thumbnail findThumbnail(String id){
        return em.find(Thumbnail.class, id);
    }


    //썸네일 삭제

    @Transactional
    public void deleteThumbnail(String id){
        Thumbnail thumbnail = findThumbnail(id);
        em.remove(thumbnail);

    }

    //썸네일 생성 및 저장
    @Transactional
    public Thumbnail saveThumbnail(MultipartFile file) throws IOException{
        String imagePath = uploadImage(file);
        Thumbnail thumbnail = new Thumbnail(createStoreFileName(file.getOriginalFilename()),
                imagePath, file.getOriginalFilename());


        //썸네일엔티티를 영속성 컨텍스트에 불러옴
        String thumbNailId = save(thumbnail);
        Thumbnail finalThumbNail = findThumbnail(thumbNailId);

        return finalThumbNail;

    }

    @Value("${firebase.bucket}")
    private String firebaseBucket;


    //path
    public String uploadImage(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        String originFileName = file.getOriginalFilename();
        String storeFileName = createStoreFileName(originFileName);
        InputStream image = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create("thumbnail/"+storeFileName, image, file.getContentType());
        return getFirebaseImagePath(blob.getMediaLink());
    }


    //고유한 저장이름 생성 -> id
    private String createStoreFileName(String originFileName){
        String uuid = UUID.randomUUID().toString();
        String extendsName = extracted(originFileName);
        return uuid+"."+extendsName;
    }


    //확장자명 추출 ->
    private String extracted(String originFileName){
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos+1);
    }


    //이미지경로 호출
    private String getFirebaseImagePath(String meadiaLink){
        int start = meadiaLink.lastIndexOf("%");
        int last = meadiaLink.lastIndexOf("?");
        return "https://firebasestorage.googleapis.com/v0/b/"+ firebaseBucket +"/o/thumbnail%"
                +meadiaLink.substring(start+1,last)
                +"?alt=media";
    }
}
