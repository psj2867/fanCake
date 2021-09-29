package ml.psj2867.demo.service.video.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class VideoDto{

    private String title;
    private double marketCap;
    private double pricePerShare;
    private int currentAmount;
    private int videoIdx;
    private LocalDateTime expirationDate;

}