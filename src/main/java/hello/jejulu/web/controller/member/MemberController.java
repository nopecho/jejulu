package hello.jejulu.web.controller.member;

import hello.jejulu.service.member.MemberService;
import hello.jejulu.web.consts.SessionConst;
import hello.jejulu.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 폼 페이지 요청 핸들러
     * @param memberSaveDto : 회원 저장 DTO
     */
    @GetMapping("/sign-up")
    public String memberSaveForm(@ModelAttribute MemberDto.Save memberSaveDto){
        return "jejulu/sign/sign-up-member-form";
    }

    /**
     * 회원가입시 로그인 아이디 중복 확인 핸들러
     * @param checkedId :(@ReqeusetParam으로 요청 파라미터값 받아옴) 중복 확인 할 ID
     */
    @ResponseBody
    @GetMapping("/id-check")
    public boolean memberIdcheck(@RequestParam(name = "v") String checkedId){
        return memberService.isDuplicateId(checkedId);
    }

    /**
     * 회원 정보 조회 핸들러
     * @param lookupInfo : @RequestParam 값으로 member요청인지 확인
     * @param loginMember : @SessionAttribute(name)으로 해당 요청 클라이언트의 세션을 name으로 조회, 로그인 된 회원정보DTO 반환
     * @param redirectAttributes : redirect시 redirect값을 설정해서 return
     */
    @GetMapping("/info")
    public String lookupMemberInfo(@RequestParam(name = "v") String lookupInfo,
                                   @SessionAttribute(name = SessionConst.MEMBER) MemberDto.Info loginMember,
                                   RedirectAttributes redirectAttributes){
        if(!lookupInfo.equals("member") || loginMember == null){
            return null;
        }
        redirectAttributes.addAttribute("memberId",loginMember.getId());
        return "redirect:/members/{memberId}";
    }

    /**
     * 회원 가입 핸들러
     * @param memberSaveDto : @ModelAttribute로 요청 Form의 input값이랑 해당 객채의 필드랑 바인딩해서 반환
     * @param bindingResult : 유효성 검증 실패 시 해당 객체에 오류를 담음
     */
    @PostMapping
    public String memberSave(@ModelAttribute @Validated MemberDto.Save memberSaveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info(bindingResult.getFieldErrors().get(0).getDefaultMessage());
            return "jejulu/sign/sign-up-member-form";
        }
        memberService.add(memberSaveDto);
        return "jejulu/success/success-sign-up";
    }

    /**
     * 회원 상세 조회 핸들러
     * @param memberId : @PathVariable = 경로 변수 / 해당 값(회원번호)으로 Repository에서 회원 조회
     * @param loginMember : @SessionAttribute(name)으로 해당 요청 클라이언트의 세션을 name으로 조회, 로그인 된 회원정보DTO 반환
     * @param model : 조회 된 회원을 저장하기 위한 Model 객체
     */
    @GetMapping("/{memberId}")
    public String memberInfo(@PathVariable Long memberId,
                             @SessionAttribute(name = SessionConst.MEMBER) MemberDto.Info loginMember,
                             Model model){
        if(loginMember == null || loginMember.getId() != memberId){
            return null;
        }

        MemberDto lookupMember = memberService.lookupMember(memberId);
        if(lookupMember == null){
            return null;
        }

        model.addAttribute("update",lookupMember);

        return "jejulu/members/member";
    }

    /**
     * 회원 수정 폼 요청 핸들러
     * @param memberId : @PathVariable = 경로 변수 / 해당 값(회원번호)으로 Repository에서 회원 조회
     * @param model : 조회 된 회원을 저장하기 위한 Model 객체
     */
    @GetMapping("/{memberId}/edit")
    public String memberUpdateForm(@PathVariable Long memberId, Model model){
        MemberDto lookupMember = memberService.lookupMember(memberId);
        model.addAttribute("update",lookupMember);
        return "jejulu/members/member-update-form";
    }

    /**
     * 회원 수정 핸들러
     * @param memberId : @PathVariable = 경로 변수 / 해당 값(회원번호)으로 Repository에서 회원 조회
     * @param memberUpdateDto : @ModelAttribute로 요청 Form의 input값이랑 해당 객채의 필드랑 바인딩해서 반환
     * @param session : 회원 수정 완료 시 세션값 업데이트
     */
    @PatchMapping("/{memberId}")
    public String updateMember(@PathVariable Long memberId,
                               @ModelAttribute @Validated MemberDto.Update memberUpdateDto,
                               BindingResult bindingResult,
                               HttpSession session){
        if(bindingResult.hasErrors()){
            return "jejulu/members/member-update-form";
        }
        MemberDto.Info updateMember = memberService.edit(memberId, memberUpdateDto);
        session.setAttribute(SessionConst.MEMBER,updateMember);
        return "redirect:/members/{memberId}";
    }

    /**
     * 회원 삭제 핸들러
     * @param memberId : 삭제 할 회원 번호
     * @param session : 삭제 성공 시 세션 만료
     */
    @DeleteMapping("/{memberId}")
    public String deleteMember(@PathVariable Long memberId, HttpSession session){
        if(!memberService.remove(memberId)){
            return null;
        }
        session.invalidate();
        return "redirect:/";
    }
}
