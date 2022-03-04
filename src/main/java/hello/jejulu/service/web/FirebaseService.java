package hello.jejulu.service.web;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class FirebaseService {

    @Value("${firebase.bucket}")
    private String firebaseBucket;

    public String uploadImage(MultipartFile file, String storeFileName) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);
        InputStream image = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create("thumbnail/"+storeFileName, image, file.getContentType());
        return getFirebaseImagePath(blob.getMediaLink());
    }

    private String getFirebaseImagePath(String meadiaLink){
        int start = meadiaLink.lastIndexOf("%");
        int last = meadiaLink.lastIndexOf("?");
        return "https://firebasestorage.googleapis.com/v0/b/"+ firebaseBucket +"/o/thumbnail%"
                +meadiaLink.substring(start+1,last)
                +"?alt=media";
    }
}
