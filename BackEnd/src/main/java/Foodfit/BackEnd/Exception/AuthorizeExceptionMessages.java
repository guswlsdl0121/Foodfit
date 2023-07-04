package Foodfit.BackEnd.Exception;

public enum AuthorizeExceptionMessages {

    UNAVAILABLE_TOKEN("토큰이 없거나, 형식이 틀립니다."),
    CANNOT_FIND_USER_FROM_TOKEN("토큰으로부터 유저를 추출할 수 없습니다."),
    INVALID_JWT_SIGNATURE("JWT signature이 유효하지 않습니다."),
    UNSUPPORTED_JWT("지원하지 않는 JWT Token 형식입니다."),
    EXPIRED_JWT("만료된 엑세스 토큰입니다."),
    CANNOT_MATCH_USER("게시글을 수정, 삭제할 권한이 없습니다.");

    public String MESSAGE;

    AuthorizeExceptionMessages(String msg) {
        this.MESSAGE = msg;
    }
}
