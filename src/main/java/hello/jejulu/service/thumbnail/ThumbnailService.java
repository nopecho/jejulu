//package hello.jejulu.service.thumbnail;
//
//import hello.jejulu.domain.thumbnail.Thumbnail;
//import hello.jejulu.domain.thumbnail.ThumbnailRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class ThumbnailService {
//
//    private final ThumbnailRepository thumbnailRepository;
//    private final FirebaseService firebaseService;
//
//    /**
//     * Thumbnail 생성
//     * @param imageFile Controller 계층의 Dto에서 넘어온 저장할 File객체
//     * @return Thumbnail객체 (Entity)
//     * @throws IOException 파일 입출력 예외
//     */
//    public Thumbnail create(MultipartFile imageFile) throws IOException {
//        if(imageFile.isEmpty()){
//            return null;
//        }
//        String originalFilename = imageFile.getOriginalFilename();
//        String thumbnailId = createThumbnailId(originalFilename);
//        String imagePath = firebaseService.uploadImage(imageFile, thumbnailId);
//        return thumbnailRepository.save(new Thumbnail(thumbnailId, imagePath, originalFilename));
//    }
//
//    /**
//     * Thumbnail 수정
//     * @param imageFile Controller 계층의 Dto에서 넘어온 수정할 File객체
//     * @param thumbnail 수정 전 기존 Thumbnail 객체 (Entity)
//     * @return 수정 후 Thumbnail객체
//     * @throws IOException 파일 입출력 예외
//     */
//    public Thumbnail update(MultipartFile imageFile, Thumbnail thumbnail) throws IOException {
//        if(imageFile.isEmpty()){
//            return thumbnail;
//        }
//        if(thumbnail == null){
//            return create(imageFile);
//        }
//        String updateFileName = imageFile.getOriginalFilename();
//        String updatePath = firebaseService.updateImage(imageFile, thumbnail.getId());
//        thumbnail.updateInfo(updatePath, updateFileName);
//        return thumbnail;
//    }
//
//    /**
//     * Thumnbnail ID 생성
//     * @param originFileName File객체의 실제 파일명 변수
//     * @return uuid가 적용된 Thumbnail ID
//     */
//    private String createThumbnailId(String originFileName){
//        String uuid = UUID.randomUUID().toString();
//        String extendsName = extracted(originFileName);
//        return uuid+"."+extendsName;
//    }
//
//    /**
//     * 파일 확장자 추출
//     * @return 확장자 명
//     */
//    private String extracted(String originFileName){
//        int pos = originFileName.lastIndexOf(".");
//        return originFileName.substring(pos+1);
//    }
//}
