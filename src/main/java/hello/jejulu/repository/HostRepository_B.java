package hello.jejulu.repository;

import hello.jejulu.domain.host.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HostRepository_B {

    private final EntityManager em;

    //호스트 저장
    public void save(Host host){

        if(host.getId()==null){
            em.persist(host);
        }
        else{
            em.merge(host);
        }
    }

    //PK로 호스트 조회
    public Host findByPk(Long id){

        return findAll().stream()
                .filter(h-> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //아이디로 호스트 조회
    public Optional<Host> findByLoginId(String loginId){
        return findAll().stream()
                .filter(h-> h.getLoginId().equals(loginId))
                .findFirst();
    }

    //호스트 모두 조회
    public List<Host> findAll(){
        return em.createQuery("select h from Host h",Host.class)
                .getResultList();
    }

    //이름으로 호스트 찾기
    public List<Host> findByName(String name){
        return em.createQuery("select h from Host h where h.hostName=:name",Host.class)
                .setParameter("name",name)
                .getResultList();
    }
}
