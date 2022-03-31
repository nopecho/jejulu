package hello.jejulu.service.web;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Slf4j
@Service
public class FirebaseService {


    // Firebase Bucket 명
    @Value("${firebase.bucket}")
    private String firebaseBucket;

    /**
     * Firebase 이미지 업로드
     * @param file 업로드할 이미지 파일
     * @param storeFileName Firebase에 저장할 파일명
     * @return 저장된 Firebase 이미지 경로
     */
    public String uploadImage(MultipartFile file, String storeFileName) throws IOException {
        Blob blob = firebaseUpload(file, storeFileName);
        return getFirebaseImagePath(blob.getMediaLink());
    }

    /**
     * Firebase 이미지 수정
     * @param file 수정할 이미지 파일
     * @param oldStoreFileName 기존 저장된 이미지 파일명
     * @return 저장된 Firebase 이미지 경로
     */
    public String updateImage(MultipartFile file, String oldStoreFileName) throws IOException{
        Blob blob = firebaseUpload(file, oldStoreFileName);
        return getFirebaseImagePath(blob.getMediaLink());
    }

    // 이미지 경로 추출
    private String getFirebaseImagePath(String meadiaLink){
        int start = meadiaLink.lastIndexOf("%");
        int last = meadiaLink.lastIndexOf("?");
        return "https://firebasestorage.googleapis.com/v0/b/"+ firebaseBucket +"/o/thumbnail%"
                +meadiaLink.substring(start+1,last)
                +"?alt=media";
    }

    private Blob firebaseUpload(MultipartFile file, String storeFileName) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream image = new ByteArrayInputStream(file.getBytes());
        return bucket.create("thumbnail/"+storeFileName, image, file.getContentType());
    }
}
