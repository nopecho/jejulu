package hello.jejulu.service.booking;

import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.booking.BookingRepository;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.member.MemberRepository;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.service.util.ServiceUtil;
import hello.jejulu.web.dto.BookingDto;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
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
    public PostDto.Info getPostToBooking(Long postId) {
        Post post = ServiceUtil.getEntityByNullCheck(postRepository.findById(postId));
        return new PostDto.Info(post, ServiceUtil.extractedPath(post.getThumbnail()));
    }
}
