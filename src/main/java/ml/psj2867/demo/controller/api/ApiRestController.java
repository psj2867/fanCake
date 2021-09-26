package ml.psj2867.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.demo.service.advancebooking.AdvanceBookingService;
import ml.psj2867.demo.service.advancebooking.model.AdvanceBookingForm;


@RestController
@RequestMapping("api")
public class ApiRestController {
    
    @Autowired
    private AdvanceBookingService adBookingService;

    @PostMapping("advance")
    public ResponseEntity postAdvance(AdvanceBookingForm reservationForm){
        return ResponseEntity.ok( adBookingService.addBooking() );
    }

}