package ml.psj2867.fancake.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;

import ml.psj2867.fancake.service.api.model.ErrorDto;


public class FieldValidException extends ApiException{
    private static final long serialVersionUID = -3982470902307575402L;

    private List<ErrorDto> errors = new ArrayList<>();

    public List<ErrorDto> getErrors(MessageSource messageSource){
        return this.errors.stream()
                            .map(errorL -> errorL.resolve(messageSource))
                            .collect(Collectors.toList());
    }
    public void add(ErrorDto error){this.errors.add(error);}

    public FieldValidException(ErrorDto error) {super();this.errors.add(error);}
    public FieldValidException(Collection<ErrorDto> errors) {super();this.errors.addAll(errors);}

    public FieldValidException() {super();}    
    public FieldValidException(java.lang.String message) {super(message);}
    public FieldValidException(java.lang.String message, java.lang.Throwable cause) { super(message, cause);}    
    public FieldValidException(java.lang.Throwable cause) {super(cause);}

    public static FieldValidException of(ErrorDto error){
        return new FieldValidException(error);
    }
    public static FieldValidException of(Collection<ErrorDto> errors){
        return new FieldValidException(errors);
    }
}