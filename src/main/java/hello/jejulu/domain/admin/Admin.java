package hello.jejulu.domain.admin;

import hello.jejulu.domain.BaseTimeEntity;
import hello.jejulu.domain.Role;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 1)
    private Role role;
}
