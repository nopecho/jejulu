package hello.jejulu.service.booking;

import hello.jejulu.web.dto.BookingDto;
import hello.jejulu.web.dto.PostDto;

public interface BookingService {
    BookingDto.Info addBooking(BookingDto.Save bookingSaveDto, Long memberId, Long postId);
    PostDto.Info getPostToBooking(Long postId);
}
