package hello.jejulu.web.dto;

import hello.jejulu.domain.booking.Booking;
import hello.jejulu.domain.member.Member;
import hello.jejulu.domain.post.Post;
import hello.jejulu.domain.util.Category;
import hello.jejulu.service.util.ServiceUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class BookingDto {

    @Getter @Setter
    public static class Save{

        @NotBlank(message = "이름은 필수로 입력 해야돼요.")
        @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름에 공백, 숫자, 특수문자가 들어갈 수 없어요")
        private String name;

        @NotNull(message = "날짜는 필수로 입력 해야돼요.")
        @FutureOrPresent(message = "오늘 이전의 날짜는 예약할 수 없어요.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate bookDate;

        @Range(min = 1,max = 5,message = "인원 수는 1 ~ 5명 사이여야 돼요.")
        private int personCount;

        @NotBlank(message = "전화번호는 필수로 입력 해야돼요.")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식을 다시 확인 해주세요")
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

    @Getter @Setter
    public static class MyDetail{
        private Long id;
        private Category category;
        private LocalDate bookDate;
        private int personCount;
        private String imagePath;

        public MyDetail(Booking booking){
            this.id = booking.getId();
            this.category = booking.getPost().getCategory();
            this.bookDate = booking.getDate();
            this.personCount = booking.getPersonCount();
            this.imagePath = ServiceUtil.extractedPath(booking.getPost().getThumbnail());
        }
    }

    @Getter @Setter
    public static class Detail{
        private Long id;
        private String name;
        private LocalDate bookDate;
        private int personCount;
        private String phone;
        private Long postId;
        private Category category;
        private String postTitle;
        private String hostName;
        private String hostPhone;
        private String hostEmail;
        private String hostAddr;

        public Detail(Booking booking){
            this.id = booking.getId();
            this.name = booking.getName();
            this.bookDate = booking.getDate();
            this.personCount = booking.getPersonCount();
            this.phone = booking.getPhone();
            this.postId = booking.getPost().getId();
            this.category = booking.getPost().getCategory();
            this.postTitle = booking.getPost().getTitle();
            this.hostName = booking.getPost().getHost().getName();
            this.hostPhone = booking.getPost().getHost().getPhone();
            this.hostEmail = booking.getPost().getHost().getEmail();
            this.hostAddr = booking.getPost().getHost().getAddr();
        }
    }

    @Getter @Setter
    public static class MemberDetail{
        private Long id;
        private String name;
        private LocalDate bookDate;
        private int personCount;
        private Long postId;
        private String imagePath;
        private String memberName;
        private String memberPhone;
        private String memberEmail;

        public MemberDetail(Booking booking){
            this.id = booking.getId();
            this.name = booking.getName();
            this.bookDate = booking.getDate();
            this.personCount = booking.getPersonCount();
            this.postId = booking.getPost().getId();
            this.imagePath = ServiceUtil.extractedPath(booking.getPost().getThumbnail());
            this.memberName = booking.getMember().getName();
            this.memberPhone = booking.getMember().getPhone();
            this.memberEmail = booking.getMember().getEmail();
        }
    }
}
