package ml.psj2867.demo.service.advancebooking.model;

import ml.psj2867.demo.entity.AdvanceBookingEntity;

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