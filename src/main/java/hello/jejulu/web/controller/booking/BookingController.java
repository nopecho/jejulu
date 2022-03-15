package hello.jejulu.web.controller.booking;

import hello.jejulu.service.booking.BookingService;
import hello.jejulu.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/posts/{postId}")
    public String bookingForm(@PathVariable Long postId, Model model){
        PostDto.Info postInfo = bookingService.getPostToBooking(postId);
        model.addAttribute("postInfo",postInfo);
        return "jejulu/bookings/booking-form";
    }
}
