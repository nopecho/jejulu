package hello.jejulu.web.controller.booking;

import hello.jejulu.service.booking.BookingService;
import hello.jejulu.web.dto.BookingDto;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping
    public String bookingCreate(@ModelAttribute BookingDto.Save bookingSaveDto,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "jejulu/bookings/booking-form";
        }
        log.info(bookingSaveDto.getBookDate().toString());
        return "jejulu/success/success-bookings";
    }
}
