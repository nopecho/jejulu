package hello.jejulu.service.util;

import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;

import java.util.Optional;

public class ServiceUtil {

    public static <T> T getEntityByNullCheck(Optional<T> findEntity){
        T entity = findEntity.orElse(null);
        if ( entity == null ){
            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
        }
        return entity;
    }

    public static String extractedPath(Thumbnail thumbnail){
        String imagePath = "";
        if (thumbnail != null){
            imagePath = thumbnail.getPath();
        }
        return imagePath;
    }
}
