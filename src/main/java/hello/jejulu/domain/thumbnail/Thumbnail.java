package hello.jejulu.domain.thumbnail;

import hello.jejulu.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Thumbnail extends BaseTimeEntity {

    //UUID
    @Id
    @Column(name="thumbnail_Id")
    private String id;

    //이미지 경로
    @Column(nullable = false)
    private String path;

    //고유이름
    @Column(nullable = false)
    private String originName;

    public void updateEntityThumbnail(String path,String originName){

       this.path=path;
       this.originName=originName;

    }

}
