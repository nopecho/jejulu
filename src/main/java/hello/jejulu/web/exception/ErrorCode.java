package hello.jejulu.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_AUTH("권한이 없습니다."),
    BAD_REQUEST("잘못된 요청입니다."),
    MEMBER_NOT_FOUND("찾는 회원이 없습니다."),
    MEMBER_REMOVE_FAIL("해당 회원을 삭제 할 수 없습니다."),
    NOT_LOGIN_ADMIN("관리자 로그인이 필요한 서비스 입니다.");

    private final String detail;
}
