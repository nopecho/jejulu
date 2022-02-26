package hello.jejulu.domain.member;

import hello.jejulu.domain.BaseTimeEntity;
import hello.jejulu.domain.Role;
import hello.jejulu.domain.booking.Booking;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(length = 13)
    private String phone;

    @Column(length = 50)
    private String email;

    @Column(nullable = false, length = 1)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Booking> bookings = new ArrayList<>();
}
