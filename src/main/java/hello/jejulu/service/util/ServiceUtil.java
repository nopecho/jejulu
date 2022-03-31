package hello.jejulu.service.util;

import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;

import java.util.Optional;

public class ServiceUtil {

    /**
     * Null 체크 된 순수 엔티티 조회
     * @param findEntity Repository에서 조회한 Optional Entity
     * @param <T> Entity Type
     * @return Entity
     */
    public static <T> T getEntityByNullCheck(Optional<T> findEntity){
        T entity = findEntity.orElse(null);
        if ( entity == null ){
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        return entity;
    }

    /**
     * Thumbnail 이미지 경로 추출
     * @param thumbnail 추출할 Thumbnail Entity 객체
     * @return 추출한 이미지 경로 값
     */
    public static String extractedPath(Thumbnail thumbnail){
        String imagePath = "";
        if (thumbnail != null){
            imagePath = thumbnail.getPath();
        }
        return imagePath;
    }
}
