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
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.exception.ApiException;
import ml.psj2867.fancake.exception.bad.BadRequesetException;
import ml.psj2867.fancake.exception.bad.FieldValidException;
import ml.psj2867.fancake.exception.forbidden.ForbiddenException;
import ml.psj2867.fancake.exception.forbidden.NotOwnerException;
import ml.psj2867.fancake.exception.notfound.NotFoundException;
import ml.psj2867.fancake.exception.notfound.ResourceNotFoundException;
import ml.psj2867.fancake.exception.unauth.UnAuthorizedException;
import ml.psj2867.fancake.service.api.model.ErrorDto;
import ml.psj2867.fancake.util.MessageDto;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandlerAdvice {
    @Autowired
    private MessageSource messageSource;

    
    // 400
    @ExceptionHandler(BadRequesetException.class)
    public ResponseEntity<List<ErrorDto>> BadRequesetException(final HttpServletRequest request,
            final HttpServletResponse reponse, final BadRequesetException e) {
        this.log(request, reponse, e);
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ErrorDto>> BindException(final HttpServletRequest request,
            final HttpServletResponse reponse, final BindException e) {
        this.log(request, reponse, e);
        final List<ErrorDto> errors = e.getFieldErrors().stream().map(errorL -> ErrorDto.of(errorL))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(FieldValidException.class)
    public ResponseEntity<List<ErrorDto>> FiledValidException(final HttpServletRequest request,
            final HttpServletResponse reponse, final FieldValidException e) {
        this.log(request, reponse, e);
        return ResponseEntity.badRequest().body(e.getErrors(messageSource));
    }
    
    // 401
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<MessageDto> UnAuthorizedException(final HttpServletRequest request,
            final HttpServletResponse reponse, final UnAuthorizedException e) {
        this.log(request, reponse, e);
        return this.errorMessage(HttpStatus.UNAUTHORIZED, "Unauthorized", e);
    }

    // 403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<MessageDto> ForbiddenException(final HttpServletRequest request,
            final HttpServletResponse reponse, final ForbiddenException e) {
        this.log(request, reponse, e);
        return this.errorMessage(HttpStatus.FORBIDDEN, "No authority", e);
    }
    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<MessageDto> NotOwnerException(final HttpServletRequest request,
            final HttpServletResponse reponse, final NotOwnerException e) {
        this.log(request, reponse, e);
        return this.errorMessage(HttpStatus.FORBIDDEN, NotOwnerException.DEFAULT_MSG, e);
    }

    // 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundException(final HttpServletRequest request,
            final HttpServletResponse reponse, final NotFoundException e) {
        this.log(request, reponse, e);
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> ResourceNotFoundException(final HttpServletRequest request,
            final HttpServletResponse reponse, final ResourceNotFoundException e) {
        this.log(request, reponse, e);
        final String message = String.format("'%s' - '%s' is not found", e.getResource(), e.getRejectedValue());
        return this.errorMessage(HttpStatus.NOT_FOUND, message, e);
    }

    // 415
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorDto> HttpMediaTypeNotSupportedException(final HttpServletRequest request,
            final HttpServletResponse reponse, final HttpMediaTypeNotSupportedException e) {
        this.log(request, reponse, e);
        ErrorDto error = ErrorDto.of("HttpMediaTypeNotSupported", "HEADER" , e.getContentType().toString() ,e.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body(error);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public Object Exception(final HttpServletRequest request, final HttpServletResponse reponse, final Exception e) {
        log.error("url - {}, paramter - {}", request.getRequestURL(), convertParameterMap(request), e);
        return ResponseEntity.internalServerError().body(e);
    }

    
    private void log(final HttpServletRequest request, final HttpServletResponse reponse, final Exception e) {
        log.warn("url - {}, paramter - {}", request.getRequestURL(), convertParameterMap(request), e);
    }

    private ResponseEntity<MessageDto> errorMessage(HttpStatus httpStatus, String defaultMsg, ApiException e){
        BodyBuilder builder = ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE);        
        return errorMessage(builder, defaultMsg, e);
    }
    private ResponseEntity<MessageDto> errorMessage(BodyBuilder builder, String defaultMsg, ApiException e){
        if(e.getMessageDto() == null)
            return builder.body(MessageDto.of(defaultMsg));
        return builder.body(e.getMessageDto());
    }

    private Map<String, String> convertParameterMap(final HttpServletRequest request) {
        Map<String, String[]> m = request.getParameterMap();
        final Map<String, String> result = new HashMap<>();
        for (final Entry<String, String[]> each : m.entrySet()) {
            String value = String.join(",", Arrays.asList(each.getValue()));
            result.put(each.getKey(), value);
        }
        return result;
    }

}