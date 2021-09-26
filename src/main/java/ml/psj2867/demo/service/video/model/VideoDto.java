package ml.psj2867.demo.service.video.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VideoDto{

    private String title;
    private double marketCap;
    private String currentAmount;
    private int videoIdx;
    private LocalDateTime expirationDate;
}