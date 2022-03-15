package hello.jejulu.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class BookingDto {

    @Getter @Setter
    public static class Save{

        @NotBlank
        private String name;

        @NotBlank
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate bookDate;

        @NotBlank
        private int personCount;

        @NotBlank
        private String phone;
    }
}
