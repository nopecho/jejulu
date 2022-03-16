package hello.jejulu.web.controller.booking;

import hello.jejulu.service.booking.BookingService;
import hello.jejulu.web.annotation.Login;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.BookingDto;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.PostDto;
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

                                        @Login MemberDto.Info loginMember,
                                        Model model){
        if( bindingResult.hasErrors() ){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(bookingSaveDto);
//        if(bindingResult.hasErrors()){
//            bookingForm(postId, bookingSaveDto, model);
//        }
//        bookingService.addBooking(bookingSaveDto, loginMember.getId(), postId);
//        return "jejulu/success/success-bookings";
    }
}
