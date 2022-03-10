package hello.jejulu.service.member;

import hello.jejulu.web.dto.MemberDto;

import java.util.List;

public interface MemberService {
    MemberDto lookupMember(Long memberId);
    boolean isDuplicateId(String checkId);
    MemberDto.Info add(MemberDto.Save memberSaveDto);
    MemberDto.Info edit(Long memberId, MemberDto.Update memberUpdateDto);
    boolean remove(Long memberId);
    List<MemberDto> selectAll();
}
