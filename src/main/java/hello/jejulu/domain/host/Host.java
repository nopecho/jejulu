package hello.jejulu.domain.host;

import hello.jejulu.domain.converter.RoleConverter;
import hello.jejulu.domain.util.BaseTimeEntity;
import hello.jejulu.domain.util.Role;
import hello.jejulu.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="host")

public class Host extends BaseTimeEntity {

    @Id@GeneratedValue
    @Column(name="host_id")
    private Long id;

    @Column(nullable = false,length=20,unique=true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String hostName;

    @Column(nullable = false,length = 50)
    private String address;

    @Column(length=50)
    private String email;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "host",cascade = CascadeType.ALL)
    private List<Post> post = new ArrayList<>();




}
