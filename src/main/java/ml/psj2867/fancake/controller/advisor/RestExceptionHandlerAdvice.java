package ml.psj2867.fancake.controller.advisor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.exception.FieldValidException;
import ml.psj2867.fancake.exception.NotFoundException;
import ml.psj2867.fancake.exception.UnAuthorizedException;
import ml.psj2867.fancake.service.api.model.ErrorDto;
import ml.psj2867.fancake.util.MessageDto;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandlerAdvice {
    @Autowired
    private MessageSource messageSource;

    // 500
    @ExceptionHandler(Exception.class)
    public Object Exception(final HttpServletRequest request, final HttpServletResponse reponse, final Exception e) {
        this.log(request, reponse, e);
        log.error("exceptionName - {} , message - {} ", e.toString(), e.getMessage());
        return ResponseEntity.internalServerError().body(e);
    }

    // 400
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ErrorDto>> BindException(final HttpServletRequest request,
            final HttpServletResponse reponse, final BindException e) {
        this.log(request, reponse, e);
        final List<ErrorDto> errors = e.getFieldErrors().stream().map(errorL -> ErrorDto.of(errorL))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    // 400
    @ExceptionHandler(FieldValidException.class)
    public ResponseEntity<List<ErrorDto>> FiledValidException(final HttpServletRequest request,
            final HttpServletResponse reponse, final FieldValidException e) {
        this.log(request, reponse, e);
        return ResponseEntity.badRequest().body(e.getErrors(messageSource));
    }

    // 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageDto> NotFoundException(final HttpServletRequest request,
            final HttpServletResponse reponse, final NotFoundException e) {
        this.log(request, reponse, e);
        final String message = String.format("%s-%s is nof found", e.getResource(), e.getRejectedValue());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageDto.of(message));
    }

    // 500
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<MessageDto> UnAuthorizedException(final HttpServletRequest request,
            final HttpServletResponse reponse, final UnAuthorizedException e) {
        this.log(request, reponse, e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageDto.of("Unauthorized"));
    }

    private void log(final HttpServletRequest request, final HttpServletResponse reponse, final Exception e) {
        log.warn("url - {}, paramter - {}", request.getRequestURL(), convert(request.getParameterMap()), e);
    }

    private Map<String, String> convert(final Map<String, String[]> m) {
        final Map<String, String> result = new HashMap<>();
        for (final Entry<String, String[]> each : m.entrySet()) {
            String value = String.join(",", Arrays.asList(each.getValue()));
            result.put(each.getKey(), value);
        }
        return result;
    }

}