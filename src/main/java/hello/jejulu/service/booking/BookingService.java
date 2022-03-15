package hello.jejulu.service.booking;

import hello.jejulu.web.dto.PostDto;

public interface BookingService {
    PostDto.Info getPostToBooking(Long postId);
}
