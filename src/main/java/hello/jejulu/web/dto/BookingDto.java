package hello.jejulu.web.dto;

import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class BookingDto {

    @Getter @Setter
    public static class Save{

        @NotBlank
        private String name;

        @NotNull
        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate bookDate;

        @Range(min = 1,max = 6)
        private int personCount;

        @NotBlank
        @Size(min = 10, max = 13)
        private String phone;

        public Booking toEntity(Member member, Post post){
            return Booking.builder()
                    .name(this.name)
                    .date(this.bookDate)
                    .personCount(this.personCount)
                    .phone(this.phone)
                    .member(member)
                    .post(post)
                    .build();
        }
    }

    @Getter @Setter
    public static class Info{
        private Long id;
        private String name;
        private LocalDate bookDate;
        private int personCount;
        private String phone;

        public Info(Booking booking){
            this.id = booking.getId();
            this.name = booking.getName();
            this.bookDate = booking.getDate();
            this.personCount = booking.getPersonCount();
            this.phone = booking.getPhone();
        }
    }
}
