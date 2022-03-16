package hello.jejulu.web.controller.booking;

import hello.jejulu.service.booking.BookingService;
import hello.jejulu.web.annotation.Login;
import hello.jejulu.web.dto.BookingDto;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.exception.CustomException;
import hello.jejulu.web.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/posts/{postId}")
    public String bookingForm(@PathVariable Long postId){
        return "redirect:/posts/{postId}";
    }

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
}
