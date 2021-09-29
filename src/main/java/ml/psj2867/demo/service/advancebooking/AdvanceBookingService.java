package ml.psj2867.demo.service.advancebooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.demo.dao.AdvanceBookingEntityDao;
import ml.psj2867.demo.entity.AdvanceBookingEntity;

@Transactional
@Service
public class AdvanceBookingService  {
    
    @Autowired
    private AdvanceBookingEntityDao reservationDao;  

    public AdvanceBookingEntity addBooking(){
        return null;
    }

}