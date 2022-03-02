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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/sign-up")
    public String memberSaveForm(@ModelAttribute MemberDto.Save memberSaveDto){
        return "jejulu/sign/sign-up-member-form";
    }

    @ResponseBody
    @GetMapping("/id-check")
    public boolean memberIdcheck(@RequestParam(name = "v") String checkedId){
        return memberService.isDuplicateId(checkedId);
    }

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

    @PostMapping
    public String memberSave(@ModelAttribute @Validated MemberDto.Save memberSaveDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info(bindingResult.toString());
            return "jejulu/sign/sign-up-member-form";
        }
        memberService.add(memberSaveDto);
        return "jejulu/success/success-sign-up";
    }

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

    @GetMapping("/{memberId}/edit")
    public String memberUpdateForm(@PathVariable Long memberId, Model model){
        MemberDto lookupMember = memberService.lookupMember(memberId);
        model.addAttribute("update",lookupMember);
        return "jejulu/members/member-update-form";
    }

    @PatchMapping("/{memberId}")
    public String updateMember(@PathVariable Long memberId,
                               @ModelAttribute MemberDto.Update memberUpdateDto,
                               HttpSession session){
        log.info("update!!");
        MemberDto.Info updateMember = memberService.edit(memberId, memberUpdateDto);
        session.setAttribute(SessionConst.MEMBER,updateMember);
        return "redirect:/members/{memberId}";
    }
}
