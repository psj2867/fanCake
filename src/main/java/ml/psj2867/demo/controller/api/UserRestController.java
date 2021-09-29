package ml.psj2867.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.psj2867.demo.service.advancebooking.AdvanceBookingService;
import ml.psj2867.demo.service.advancebooking.model.AdvanceBookingForm;


@RestController
@RequestMapping("api/user")
public class UserRestController {
    

    @GetMapping("")
    public ResponseEntity getUser(){
        return ResponseEntity.ok( "");
    }

    @PostMapping("")
    public ResponseEntity postUser(AdvanceBookingForm reservationForm){
        return ResponseEntity.ok("" );
    }



}