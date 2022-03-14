package hello.jejulu.domain.thumbnail;

import hello.jejulu.domain.util.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Thumbnail extends BaseTimeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String originName;

    public void updateInfo(String path, String originName){
        this.path = path;
        this.originName = originName;
    }
}
