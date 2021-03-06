package hello.jejulu.domain.post;

import hello.jejulu.domain.converter.CategoryConverter;
import hello.jejulu.domain.util.BaseTimeEntity;
import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.host.Host;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.domain.util.Category;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Convert(converter = CategoryConverter.class)
    @Column(nullable = false, length = 1)
    private Category category;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "thumnailId")
    private Thumbnail thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public void countPlus(){
        this.count++;
    }

    public void updateInfo(String title, String description, Category category, String content, Thumbnail thumbnail){
        this.title = title;
        this.description = description;
        this.category = category;
        this.content = content;
        this.thumbnail = thumbnail;
    }
}
