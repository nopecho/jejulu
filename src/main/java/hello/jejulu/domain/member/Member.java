package hello.jejulu.domain.member;

import hello.jejulu.domain.converter.RoleConverter;
import hello.jejulu.domain.util.BaseTimeEntity;
import hello.jejulu.domain.util.Role;
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

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(length = 50)
    private String email;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false, length = 1)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Booking> bookings = new ArrayList<>();

    public void updateInfo(String updateName, String updatePhone, String updateEmail){
        this.name = updateName;
        this.phone = updatePhone;
        this.email = updateEmail;
    }
}
