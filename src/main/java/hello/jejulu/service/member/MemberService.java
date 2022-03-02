package hello.jejulu.service.member;

import hello.jejulu.web.dto.MemberDto;

public interface MemberService {
    MemberDto.Save add(MemberDto.Save memberSaveDto);
    boolean isDuplicateId(String value);
    MemberDto lookupMember(Long memberId);
    MemberDto.Info edit(Long memberId,MemberDto.Update memberUpdateDto);
}
