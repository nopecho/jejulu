package hello.jejulu.service.booking;

import hello.jejulu.web.dto.BookingDto;

public interface BookingService {
    BookingDto.Info addBooking(BookingDto.Save bookingSaveDto, Long memberId, Long postId);
    boolean isBookedByLoginMember(Long bookId, Long memberId);
    BookingDto.Info getBookingInfoById(Long bookingId);
}
