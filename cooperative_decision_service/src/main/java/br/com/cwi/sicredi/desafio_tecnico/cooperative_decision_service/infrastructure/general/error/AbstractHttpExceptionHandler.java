package br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error;

import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.enumerator.ErrorCode;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo.HttpErrorResponse;
import br.com.cwi.sicredi.desafio_tecnico.cooperative_decision_service.infrastructure.general.error.pojo.HttpErrorResponseDetail;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractHttpExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .message(ex.getMessage())
                .code(ErrorCode.INVALID).build();

        return  new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .message(ex.getMessage())
                .code(ErrorCode.INVALID).build();

        return  new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        HttpErrorResponseDetail detail = HttpErrorResponseDetail.builder()
                .message(ex.getMessage())
                .field(ex.getVariableName())
                .code(ErrorCode.MISSING_PARAM).build();

        return  new ResponseEntity<>(new HttpErrorResponse(Collections.singletonList(detail)), new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<HttpErrorResponseDetail> details = new ArrayList<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            if (objectError instanceof FieldError) {
                details.add(HttpErrorResponseDetail.builder()
                        .resource(objectError.getObjectName())
                        .message(objectError.getDefaultMessage())
                        .field(((FieldError) objectError).getField())
                        .code(ErrorCode.MISSING_FIELD).build());
            } else {
                details.add(HttpErrorResponseDetail.builder()
                        .resource(objectError.getObjectName())
                        .message(objectError.getDefaultMessage())
                        .code(ErrorCode.MISSING_FIELD).build());
            }
        }
        return new ResponseEntity<>(new HttpErrorResponse(details), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
