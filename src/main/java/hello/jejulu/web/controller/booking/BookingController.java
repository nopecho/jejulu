package hello.jejulu.web.controller.booking;

import hello.jejulu.service.booking.BookingService;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.BookingDto;
import hello.jejulu.web.dto.MemberDto;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String bookingForm(@PathVariable Long postId,
                              @ModelAttribute BookingDto.Save bookingSaveDto,
                              Model model){
        PostDto.Info postInfo = bookingService.getPostToBooking(postId);
        model.addAttribute("postInfo",postInfo);
        return "jejulu/bookings/booking-form";
    }

    @PostMapping("/posts/{postId}")
    public String bookingCreate(@PathVariable Long postId,
                                @ModelAttribute @Validated BookingDto.Save bookingSaveDto,
                                BindingResult bindingResult,
                                @SessionAttribute(name = SessionConst.MEMBER) MemberDto.Info loginMember,
                                Model model){
        if(bindingResult.hasErrors()){
            PostDto.Info postInfo = bookingService.getPostToBooking(postId);
            model.addAttribute("postInfo",postInfo);
            return "jejulu/bookings/booking-form";
        }
        bookingService.addBooking(bookingSaveDto, loginMember.getId(), postId);
        return "jejulu/success/success-bookings";
    }
}
