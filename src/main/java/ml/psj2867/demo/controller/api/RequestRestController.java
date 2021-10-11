package ml.psj2867.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.demo.service.advancebooking.AdvanceBookingService;
import ml.psj2867.demo.service.advancebooking.model.AdvanceBookingForm;


@RestController
@RequestMapping("api/request")
public class RequestRestController {
        
    @Autowired
    private AdvanceBookingService adBookingService;

    @PostMapping("advance")
    public ResponseEntity<Object> postAdvance(AdvanceBookingForm reservationForm){
        adBookingService.addBooking(reservationForm);
        return ResponseEntity.ok( ).build();
    }
    @PostMapping("request")
    public ResponseEntity<Object> postRequest(AdvanceBookingForm reservationForm){
        adBookingService.addBooking(reservationForm);
        return ResponseEntity.ok( ).build();
    }

}