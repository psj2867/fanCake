package ml.psj2867.demo.service.advancebooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ml.psj2867.demo.dao.AdvanceBookingEntityDao;
import ml.psj2867.demo.dao.ChannelEntityDao;
import ml.psj2867.demo.entity.AdvanceBookingEntity;
import ml.psj2867.demo.entity.ChannelEntity;
import ml.psj2867.demo.service.channel.model.ChannelForm;

@Transactional
@Service
public class AdvanceBookingService  {
    
    @Autowired
    private AdvanceBookingEntityDao reservationDao;  

    public AdvanceBookingEntity addBooking(){
        return null;
    }

}