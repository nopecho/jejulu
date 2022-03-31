package hello.jejulu.web.controller.booking;

import hello.jejulu.service.booking.BookingService;
import hello.jejulu.web.annotation.Login;
import hello.jejulu.web.dto.booking.BookingDto;
import hello.jejulu.web.dto.host.HostDto;
import hello.jejulu.web.dto.member.MemberDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * 회원 예약 상세 조회 핸들러
     * @param bookingId 조회할 예약 번호
     * @param loginMember 세션에 담긴 로그인 회원 정보
     * @param model view로 넘겨줄 모델
     */
    @GetMapping("/{bookingId}")
    public String lookupBookingDetail(@PathVariable Long bookingId,
                                      @Login MemberDto.Info loginMember,
                                      Model model){
        if(!bookingService.isBookedByLoginMember(bookingId, loginMember.getId())){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        BookingDto.Detail detail = bookingService.getBookingDetail(bookingId);
        model.addAttribute("detail",detail);
        return "jejulu/bookings/booking";
    }

    /**
     * 예약한 게시물 redirect 핸들러
     * @param postId 게시물 번호
     */
    @GetMapping("/posts/{postId}")
    public String bookingPost(@PathVariable Long postId){
        return "redirect:/posts/{postId}";
    }

    /**
     * 예약 핸들러
     * @param postId 예약 할 게시물 번호
     * @param bookingSaveDto 예약 정보 DTO
     * @param bindingResult validation 검증 결과 객체
     * @param loginMember session에 있는 로그인한 회원 정보
     */
    @PostMapping("/posts/{postId}")
    public ResponseEntity<?> bookingCreate(@PathVariable Long postId,
                                           @RequestBody @Validated BookingDto.Save bookingSaveDto,
                                           BindingResult bindingResult,
                                           @Login MemberDto.Info loginMember){
        if( bindingResult.hasErrors() ){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        BookingDto.Info bookInfo = bookingService.addBooking(bookingSaveDto, loginMember.getId(), postId);
        return ResponseEntity.status(302)
                .header("location","/bookings/"+bookInfo.getId()+"/success")
                .build();
    }

    /**
     * 예약 수정 핸들러
     * @param bookingId 예약 번호
     * @param bookingUpdateDto 예약 수정 정보 DTO
     * @param bindingResult validation 검증 결과 객체
     */
    @PatchMapping("/{bookingId}")
    public ResponseEntity<?> bookingUpdate(@PathVariable Long bookingId,
                                           @RequestBody @Validated BookingDto.Update bookingUpdateDto,
                                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.ok(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(bookingService.isUpdateBookingSuccess(bookingId,bookingUpdateDto));
    }

    /**
     * 예약 취소 핸들러
     * @param bookingId 예약 번호
     * @param loginMember session에 있는 로그인한 회원
     */
    @ResponseBody
    @DeleteMapping("/{bookingId}")
    public boolean deleteBooking(@PathVariable Long bookingId,
                                 @Login MemberDto.Info loginMember){
        if(!bookingService.isBookedByLoginMember(bookingId, loginMember.getId())) {
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        return bookingService.isDeleteBookingSuccess(bookingId);
    }

    /**
     * 예약 성공 페이지 핸들러
     * @param bookingId 예약 번호
     * @param loginMember session에 있는 로그인한 회원
     */
    @GetMapping("/{bookingId}/success")
    public String successBooking(@PathVariable Long bookingId,
                                 @Login MemberDto.Info loginMember,
                                 Model model){
        if (!bookingService.isBookedByLoginMember(bookingId, loginMember.getId())){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        BookingDto.Info bookingInfo = bookingService.getBookingInfoById(bookingId);
        model.addAttribute("info",bookingInfo);
        return "jejulu/success/success-bookings";
    }

    /**
     * 회원 예약 내역 조회 redirect 핸들러
     * @param loginMember session에 있는 로그인한 회원
     */
    @GetMapping("/members/info")
    public String lookupMemberInfo(@Login MemberDto.Info loginMember,
                                   RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("memberId",loginMember.getId());
        return "redirect:/bookings/members/{memberId}";
    }

    /**
     * 회원 예약 내역 조회
     * @param memberId redirect받은 회원 id
     * @param loginMember session에 있는 로그인한 회원
     * @param pageable 예약 내역 페이징 객체
     */
    @GetMapping("/members/{memberId}")
    public String lookupMemberBookings(@PathVariable Long memberId,
                                       @Login MemberDto.Info loginMember,
                                       @PageableDefault(size = 4,sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                       Model model){
        if (!memberId.equals(loginMember.getId())){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        Page<BookingDto.MyDetail> page = bookingService.getBookingsByMember(memberId, pageable);
        model.addAttribute("page",page);
        model.addAttribute("maxPage",10);
        return "jejulu/bookings/bookings-member";
    }

    /**
     * 호스트 예약 현황 조회 redirect 핸들러
     * @param loginHost session에 있는 로그인한 호스트
     */
    @GetMapping("/hosts/info")
    public String lookupHostInfo(@Login HostDto.Info loginHost,
                                 RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("hostId",loginHost.getId());
        return "redirect:/bookings/hosts/{hostId}";
    }

    /**
     * 호스트 예약 현황 조회 핸들러
     * @param hostId 호스트 번호
     * @param loginHost 로그인한 호스트
     * @param pageable 예약 현황 페이징 객체
     */
    @GetMapping("/hosts/{hostId}")
    public String lookupHostBookings(@PathVariable Long hostId,
                                     @Login HostDto.Info loginHost,
                                     @PageableDefault(size = 4, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                                     Model model){
        if (!hostId.equals(loginHost.getId())){
            throw new CustomException(ErrorCode.INVALID_AUTH);
        }
        Page<BookingDto.MemberDetail> page = bookingService.getBookingsByHost(hostId, pageable);
        model.addAttribute("page",page);
        model.addAttribute("maxPage",10);
        return "jejulu/bookings/bookings-host";
    }
}
