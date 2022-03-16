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

        @NotBlank(message = "이름은 필수로 입력 해야돼요.")
        private String name;

        @NotNull(message = "날짜는 필수로 입력 해야돼요.")
        @FutureOrPresent(message = "오늘 이전의 날짜는 예약할 수 없어요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate bookDate;

        @Range(min = 1,max = 6,message = "인원 수는 1 ~ 6명 사이여야 돼요.")
        private int personCount;

        @NotBlank(message = "전화번호는 필수로 입력 해야돼요.")
        @Size(min = 10, max = 13, message = "전화번호를 다시 확인해주세요.")
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
