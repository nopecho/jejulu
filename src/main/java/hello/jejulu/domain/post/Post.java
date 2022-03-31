package hello.jejulu.domain.post;

import hello.jejulu.domain.converter.CategoryConverter;
import hello.jejulu.domain.util.BaseTimeEntity;
import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.domain.util.Category;
import lombok.*;

import javax.persistence.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@Entity@Builder
@Table(name = "post")
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id@GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column
    private String description;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false,length = 10)
    private Category category;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name="thumbnail_Id")
    private Thumbnail thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_Id")
    private Host host;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Booking> bookings=new ArrayList<>();

    public void countPlus(){
        this.count++;
    }

    //연관관계 메서드

    public void setHost(Host host){
        this.host=host;
        host.getPost().add(this);
    }

}
