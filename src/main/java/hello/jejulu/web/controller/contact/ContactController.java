package hello.jejulu.web.controller.contact;

import hello.jejulu.service.contact.ContactService;
import hello.jejulu.web.dto.contact.ContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/contact")
@Controller
public class ContactController {

    private final ContactService contactService;

    /**
     * 문의글 작성 Form요청 핸들러
     */
    @GetMapping
    public String contactForm(@ModelAttribute ContactDto contactDto){
        return "jejulu/etc/contact-form";
    }

    /**
     * 문의글 작성 핸들러
     * @param contactDto 문의글 정보 DTO
     * @param bindingResult validation 검증 객체
     * @param request session값 얻기 위한 HttpServletRequest 객체
     */
    @PostMapping
    public String contactCreate(@ModelAttribute @Validated ContactDto contactDto,
                                BindingResult bindingResult,
                                HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "jejulu/etc/contact-form";
        }
        HttpSession session = request.getSession(false);
        contactService.createContact(contactDto,session);
        return "redirect:/success/contact";
    }

    /**
     * 문의글 삭제 핸들러
     * @param contactId 문의글 번호
     */
    @ResponseBody
    @DeleteMapping("/{contactId}")
    public boolean removeContact(@PathVariable Long contactId){
        return contactService.remove(contactId);
    }
}
