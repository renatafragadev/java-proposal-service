package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.component.MessageComponent;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.enumerator.ErrorCode;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo.HttpErrorResponse;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo.HttpErrorResponseDetail;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.BusinessException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.ConflictException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.InternalServerErrorException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NoContentException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.NotFoundException;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RequiredArgsConstructor
@RestControllerAdvice
public class HttpExceptionHandler extends AbstractHttpExceptionHandler {

    private final MessageComponent messageComponent;

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<Object> handleConflict(ConflictException ex) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .resource(ex.getResource())
                .message(messageComponent.get(ex.getMessage()))
                .field(ex.getField())
                .code(ErrorCode.ALREADY_EXISTS).build();

        return new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<Object> handleInternalServerError(InternalServerErrorException ex) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .resource(ex.getResource())
                .message(messageComponent.get(ex.getMessage()))
                .code(ErrorCode.THIRD_SERVICE_ERROR).build();

        return new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NoContentException.class})
    public ResponseEntity<Object> handleNoContent(NoContentException ex) {
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .resource(ex.getResource())
                .message(messageComponent.get(ex.getMessage()))
                .code(ErrorCode.MISSING).build();

        return new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .resource(ex.getResource())
                .message(messageComponent.get(ex.getMessage()))
                .code(ErrorCode.INVALID).build();

        return new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<Object> handleServiceUnavailableException(ServiceUnavailableException ex) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .resource(ex.getResource())
                .message(messageComponent.get(ex.getMessage()))
                .code(ErrorCode.THIRD_SERVICE_ERROR).build();

        return new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.SERVICE_UNAVAILABLE);
    }
}
