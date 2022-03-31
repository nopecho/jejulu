package hello.jejulu.domain.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByMemberId(Long memberId, Pageable pageable);

    @Query(value = "select b from Booking b left join Host h on b.post.host.id = h.id where h.id = :hostId" )
    Page<Booking> findAllByHostId(@Param(value = "hostId") Long hostId, Pageable pageable);
}
