package hello.jejulu.domain.contact;

import hello.jejulu.domain.util.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAll(Pageable pageable);
    Page<Contact> findAllByRole(Role role, Pageable pageable);
}
