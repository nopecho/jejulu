package hello.jejulu.service.booking;

import hello.jejulu.domain.booking.BookingRepository;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.post.PostRepository;
import hello.jejulu.domain.thumbnail.Thumbnail;
import hello.jejulu.web.dto.PostDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookingServiceImpl implements BookingService{

    private final PostRepository postRepository;
    private BookingRepository bookingRepository;

    @Override
    public PostDto.Info getPostToBooking(Long postId) {
        Post post = getPostByNullCheck(postRepository.findById(postId));
        return new PostDto.Info(post,extractPath(post.getThumbnail()));
    }

    private Post getPostByNullCheck(Optional<Post> findPost) {
        Post post = findPost.orElse(null);
        if (post == null) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        return post;
    }

    private String extractPath(Thumbnail thumbnail){
        String imagePath = "";
        if (thumbnail != null){
            imagePath = thumbnail.getPath();
        }
        return imagePath;
    }
}
