package hello.jejulu.domain.admin;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    private Long id;


    private String loginId;


    private String password;
}
