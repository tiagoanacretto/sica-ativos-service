package br.com.sica.ativosservice.exceptions;

import br.com.sica.ativosservice.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UnauthorizedException.class })
    protected ResponseEntity<ErrorResponseDto> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";

        ErrorResponseDto responseDto = new ErrorResponseDto(MessageFormat.format("[{0}] {1}",
                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()), "Acesso nao autorizado");
        return new ResponseEntity<ErrorResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
    }
}
