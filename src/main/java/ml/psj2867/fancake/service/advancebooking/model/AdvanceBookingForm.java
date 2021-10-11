package ml.psj2867.fancake.service.advancebooking.model;

import ml.psj2867.fancake.entity.AdvanceBookingEntity;

public class AdvanceBookingForm{
    private String channelUrl;
    private String email;

    public AdvanceBookingEntity toEntity(){
        AdvanceBookingEntity resevation = AdvanceBookingEntity.builder()
                                                .channelUrl(this.channelUrl)
                                                .email(this.email)
                                                .build();
        return resevation;
    }
}