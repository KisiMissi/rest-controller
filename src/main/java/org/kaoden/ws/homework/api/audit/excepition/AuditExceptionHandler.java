package org.kaoden.ws.homework.api.audit.excepition;

import org.kaoden.ws.homework.api.entry.exception.ExceptionDTO;
import org.kaoden.ws.homework.exception.SearchSecurityAuditException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class AuditExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(SearchSecurityAuditException.class)
    public @ResponseBody ExceptionDTO processNullArgumentException(SearchSecurityAuditException exception) {
        return ExceptionDTO.of(exception.getMessage());
    }

}
