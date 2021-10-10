package ml.psj2867.demo.service.video.model;

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
import ml.psj2867.demo.service.api.model.ErrorDto;

@Getter
@Setter
@SuperBuilder
public class BuyStockErrorDto extends ErrorDto{

    private int reaminSize;
    
}