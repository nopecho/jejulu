package hello.jejulu.service.booking;

import hello.jejulu.web.dto.booking.BookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingDto.Info addBooking(BookingDto.Save bookingSaveDto, Long memberId, Long postId);
    BookingDto.Detail getBookingDetail(Long bookingId);
    boolean isBookedByLoginMember(Long bookId, Long memberId);
    boolean isDeleteBookingSuccess(Long bookId);
    BookingDto.Info getBookingInfoById(Long bookingId);
    Page<BookingDto.MyDetail> getBookingsByMember(Long memberId, Pageable pageable);
    Page<BookingDto.MemberDetail> getBookingsByHost(Long hostId, Pageable pageable);
}
