package ml.psj2867.fancake.service.video.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ml.psj2867.fancake.service.api.model.ErrorDto;

@Getter
@Setter
@SuperBuilder
public class BuyStockSizeErrorDto extends ErrorDto{

    private long reaminSize;
    
}