package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "잘못된 요청 오류")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException {
    // message는 server.error.include-message=always 설정 해주면 잘 보임!ㄷ

    /*
    ResponseStatusExceptionResolver 예외가 해당 애노테이션을 확인해서
    오류 코드를 HttpStatus.BAD_REQUEST (400)으로 변경하고, 메시지도 담는다!!
     */
}