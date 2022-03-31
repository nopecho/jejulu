package hello.jejulu.service.booking;

import hello.jejulu.web.dto.booking.BookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    /**
     * 예약 추가
     * @param bookingSaveDto Controller계층에서 넘어온 예약 저장 정보Dto
     * @param memberId 예약을 진행하는 회원 id
     * @param postId 예약하는 게시물 id
     */
    BookingDto.Info addBooking(BookingDto.Save bookingSaveDto, Long memberId, Long postId);

    /**
     * 예약 정보 조회
     * @param bookingId 조회할 예약 id
     */
    BookingDto.Detail getBookingDetail(Long bookingId);

    /**
     * 로그인한 회원의 예약 정보 판별
     * @param bookId 예약 id
     * @param memberId 로그인한 회원 id
     */
    boolean isBookedByLoginMember(Long bookId, Long memberId);

    /**
     * 예약 수정
     * @param bookId 수정할 예약 id
     * @param bookingUpdateDto Controller계층에서 넘어온 예약 수정 정보 Dto
     */
    boolean isUpdateBookingSuccess(Long bookId, BookingDto.Update bookingUpdateDto);

    /**
     * 예약 취소
     * @param bookId 삭제할 예약 id
     */
    boolean isDeleteBookingSuccess(Long bookId);

    /**
     * 예약 정보 조회
     * @param bookingId 조회할 예약 id
     */
    BookingDto.Info getBookingInfoById(Long bookingId);

    /**
     * 회원 예약 내역 조회
     */
    Page<BookingDto.MyDetail> getBookingsByMember(Long memberId, Pageable pageable);

    /**
     * 호스트 예약 현황 조회
     */
    Page<BookingDto.MemberDetail> getBookingsByHost(Long hostId, Pageable pageable);
}
