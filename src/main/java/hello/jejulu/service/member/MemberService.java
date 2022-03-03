package hello.jejulu.service.member;

import hello.jejulu.web.dto.MemberDto;

import java.util.List;

public interface MemberService {
    MemberDto lookupMember(Long memberId);
    MemberDto.Save add(MemberDto.Save memberSaveDto);
    MemberDto.Info edit(Long memberId, MemberDto.Update memberUpdateDto);
    boolean isDuplicateId(String value);
    List<MemberDto> selectAll();
}
