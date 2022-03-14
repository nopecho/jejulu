package hello.jejulu.service.thumbnail;

import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.domain.thumbnail.ThumbnailRepository;
import hello.jejulu.service.web.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ThumbnailService {

    private final ThumbnailRepository thumbnailRepository;
    private final FirebaseService firebaseService;

    public Thumbnail create(MultipartFile imageFile) throws IOException {
        if(imageFile.isEmpty()){
            return null;
        }
        String originalFilename = imageFile.getOriginalFilename();
        String thumbnailId = createThumbnailId(originalFilename);
        String imagePath = firebaseService.uploadImage(imageFile, thumbnailId);
        return thumbnailRepository.save(new Thumbnail(thumbnailId, imagePath, originalFilename));
    }

    public Thumbnail update(MultipartFile imageFile) throws IOException {
        if(imageFile.isEmpty()){
            return null;
        }
        return null;
    }

    private String createThumbnailId(String originFileName){
        String uuid = UUID.randomUUID().toString();
        String extendsName = extracted(originFileName);
        return uuid+"."+extendsName;
    }

    private String extracted(String originFileName){
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos+1);
    }
}
