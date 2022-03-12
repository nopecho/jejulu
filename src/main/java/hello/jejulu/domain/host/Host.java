package hello.jejulu.domain.host;

import hello.jejulu.domain.converter.RoleConverter;
import hello.jejulu.domain.util.BaseTimeEntity;
import hello.jejulu.domain.util.Role;
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

    @Column(length = 50)
    private String email;

    @Column(nullable = false)
    private String addr;

    @Convert(converter = RoleConverter.class)
    @Column(nullable = false, length = 1)
    private Role role;

    @OneToMany(mappedBy = "host")
    private List<Post> posts = new ArrayList<>();

    public void updateInfo(String name, String phone, String addr, String email) {
        this.name = name;
        this.phone = phone;
        this.addr = addr;
        this.email = email;
    }

    public void addPosts(Post post) {
        this.posts.add(post);
    }
}
