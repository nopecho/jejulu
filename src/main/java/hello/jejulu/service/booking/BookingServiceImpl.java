package hello.jejulu.service.booking;

import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.booking.BookingRepository;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.service.util.ServiceUtil;
import hello.jejulu.web.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookingServiceImpl implements BookingService{

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    @Override
    public BookingDto.Info addBooking(BookingDto.Save bookingSaveDto, Long memberId, Long postId) {
        Member member = ServiceUtil.getEntityByNullCheck(memberRepository.findById(memberId));
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        Booking result = bookingRepository.save(bookingSaveDto.toEntity(member, post));
        return new BookingDto.Info(result);
    }

    @Override
    public boolean isBookedByLoginMember(Long bookId, Long memberId) {
        Member loginMember = ServiceUtil.getEntityByNullCheck(memberRepository.findById(memberId));
        return loginMember.getBookings()
                .stream()
                .anyMatch(booking -> booking.getId().equals(bookId));
    }

    @Override
    public BookingDto.Info getBookingInfoById(Long bookingId) {
        Booking booking = ServiceUtil.getEntityByNullCheck(bookingRepository.findById(bookingId));
        return new BookingDto.Info(booking);
    }

    @Override
    public Page<BookingDto.MyDetail> getBookingsByMember(Long memberId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAllByMemberId(memberId, pageable);
        return bookings.map(BookingDto.MyDetail::new);
    }

    @Override
    public Page<BookingDto.MemberDetail> getBookingsByHost(Long hostId, Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAllByHostId(hostId, pageable);
        return bookings.map(BookingDto.MemberDetail::new);
    }
}
