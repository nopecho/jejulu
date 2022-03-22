package hello.jejulu.domain.post;

import hello.jejulu.domain.BaseTimeEntity;
import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.thumbnail.Thumbnail;
import lombok.*;

import javax.persistence.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "post")
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @JoinColumn(name="thumnailId")
    private Thumbnail thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_Id")
    private Host host;

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private List<Booking> bookings=new ArrayList<>();

    public void countPlus(){
        this.count++;
    }









}
