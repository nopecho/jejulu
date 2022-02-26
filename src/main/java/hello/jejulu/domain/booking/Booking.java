package hello.jejulu.domain.booking;

import hello.jejulu.domain.BaseTimeEntity;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Booking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 2)
    private int personCount;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

}
