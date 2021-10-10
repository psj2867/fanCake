package ml.psj2867.demo.service.api.model;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto{

    private String message;
    private String code;
    private String field;
    private Object rejectedValue;

    public ErrorDto(String code, String field, Object rejectedValue){
        this.code = code;
        this.field = field;
        this.rejectedValue = rejectedValue;   
    }
    public ErrorDto(String code, String field, Object rejectedValue, String message){
        this.message = message;
        this.code = code;
        this.field = field;
        this.rejectedValue = rejectedValue;   
    }

    public ErrorDto resolve(MessageSource messageSource){
        if(! StringUtils.hasLength(this.message))
            this.message = messageSource.getMessage(this.code,null, Locale.ROOT);
        return this;
    }
    public List<ErrorDto> toList(){
        return Arrays.asList(this);
    }

    public static ErrorDto of(String code, String field, Object rejectedValue){
        return new ErrorDto(code, field, rejectedValue);
    }
    public static ErrorDto of(String code, String field, Object rejectedValue, String message){
        return new ErrorDto(code, field, rejectedValue, message);
    }

    public static ErrorDto of(FieldError error){
        return ErrorDto.builder()
                        .message(error.getDefaultMessage())
                        .code(error.getCode())
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .build();
    }
}