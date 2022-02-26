package hello.jejulu.domain.host;

import hello.jejulu.domain.BaseTimeEntity;
import hello.jejulu.domain.Role;
import hello.jejulu.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Host extends BaseTimeEntity {

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

    @Column
    private String email;

    @Column(nullable = false)
    private String addr;

    @Column(nullable = false, length = 1)
    private Role role;

    @OneToMany(mappedBy = "host")
    private List<Post> posts = new ArrayList<>();
}
