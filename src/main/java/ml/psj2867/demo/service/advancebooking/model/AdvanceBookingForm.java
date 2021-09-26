package ml.psj2867.demo.service.advancebooking.model;

import ml.psj2867.demo.entity.AdvanceBookingEntity;

public class AdvanceBookingForm{
    private String channel_id;

    public AdvanceBookingEntity toEntity(){
        AdvanceBookingEntity resevation = AdvanceBookingEntity.builder()
                                                .build();
        return resevation;
    }
}