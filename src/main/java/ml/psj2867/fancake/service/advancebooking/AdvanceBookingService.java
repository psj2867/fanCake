package ml.psj2867.fancake.service.advancebooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.fancake.dao.AdvanceBookingEntityDao;
import ml.psj2867.fancake.entity.AdvanceBookingEntity;
import ml.psj2867.fancake.service.advancebooking.model.AdvanceBookingForm;

@Transactional
@Service
public class AdvanceBookingService  {
    
    @Autowired
    private AdvanceBookingEntityDao reservationDao;  

    public AdvanceBookingEntity addBooking(AdvanceBookingForm reservationForm){
        return reservationDao.save(reservationForm.toEntity());
    }

}