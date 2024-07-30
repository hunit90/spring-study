package com.programmer.dmaker.exception;

import com.programmer.dmaker.dto.DMakerErrorReponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.programmer.dmaker.exception.DMakerErrorCode.INTERNAL_SERVER_ERROR;
import static com.programmer.dmaker.exception.DMakerErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class DMakerExceptionHandler {
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorReponse handleException (DMakerException e, HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());

        return DMakerErrorReponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public DMakerErrorReponse handleException (Exception e, HttpServletRequest request) {
        log.error("errorCode: {},  message: {}", request.getRequestURI(), e.getMessage());

        return DMakerErrorReponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR)
                .errorMessage(INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
