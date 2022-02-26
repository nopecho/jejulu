package hello.jejulu.domain.contact;

import hello.jejulu.domain.BaseTimeEntity;
import hello.jejulu.domain.Role;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Contact extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, length = 20)
    private String loginId;
}
