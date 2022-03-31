package hello.jejulu.service.member;

import hello.jejulu.web.dto.member.MemberDto;

public interface MemberService {
    /**
     * 회원 정보 조회
     * @param memberId 조회할 회원 id
     * @return 회원 DetailDto 반환
     */
    MemberDto.Detail getMemberById(Long memberId);

    /**
     * 회원 아이디 중복 체크
     * @param checkId 중복 확인 할 login Id
     */
    boolean isDuplicateId(String checkId);

    /**
     * 회원 가입
     * @param memberSaveDto Controller계층에서 넘어온 회원 저장 정보 Dto객체
     */
    MemberDto.Info add(MemberDto.Save memberSaveDto);

    /**
     * 회원 수정
     * @param memberId 수정할 회원 ID
     * @param memberUpdateDto Controller계층에서 넘어온 회줭 수정 정보 Dto객체
     */
    MemberDto.Info edit(Long memberId, MemberDto.Update memberUpdateDto);

    /**
     * 회원 삭제
     * @param memberId 삭제할 회원 ID
     */
    boolean remove(Long memberId);

}
