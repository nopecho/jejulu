package hello.jejulu.service.booking;

import hello.jejulu.domain.booking.BookingRepository;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.service.util.ServiceUtil;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookingServiceImpl implements BookingService{

    private final PostRepository postRepository;
    private BookingRepository bookingRepository;

    @Override
    public PostDto.Info getPostToBooking(Long postId) {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        return new PostDto.Info(post, ServiceUtil.extractedPath(post.getThumbnail()));
    }
}
