package me.iqpizza6349.dote.global.exception;

import me.iqpizza6349.dote.global.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ExceptionDto> definedException(BusinessException e) {
        final ExceptionDto exceptionDto = ExceptionDto.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatus())
                .body(exceptionDto);
    }

    /**
     *
     * @param runtimeException 예외 처리할 예외 클래스의 인스턴스를 매개변수로 받습니다.
     * @apiNote 매개변수는 반드시 {@code RuntimeException}의 인스턴스이어야합니다.
     * @return 예외 response. {@code ResponseEntity<ExceptionDto>}를 반환합니다.
     */
    private ResponseEntity<ExceptionDto> sendException(RuntimeException runtimeException) {
        return sendException(runtimeException, runtimeException.getMessage());
    }

    private ResponseEntity<ExceptionDto> sendException(
            RuntimeException runtimeException, String message
    ) {
        return ResponseEntity.status(getHttpStatus(runtimeException.getClass()))
                .body(new ExceptionDto(message));
    }

    /**
     *
     * @param exceptionClass 예외 처리할 클래스를 매개변수로 받습니다.
     * @apiNote 반드시 {@code RuntimeException}을 상속받은 클래스만을 받습니다.
     * @return {@code @ResponseStatus}어노테이션에 있는 {@code value}를 가져와 반환합니다.
     */
    private HttpStatus getHttpStatus(Class<? extends RuntimeException> exceptionClass) {
        return exceptionClass
                .getDeclaredAnnotation(ResponseStatus.class)
                .value();
    }

}
