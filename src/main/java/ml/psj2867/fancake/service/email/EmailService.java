package ml.psj2867.fancake.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ml.psj2867.fancake.entity.UserEntity;

@Service
public class EmailService {   
    
    @Autowired
    public JavaMailSender emailSender;

    public void sendFindPasswordEmail(final UserEntity user, String password){
        String subject = emailSubject(user);
        String content = emailContent(user, password) ;
        sendSimpleMessage( user.getEmail(), subject, content);
    }
    private String emailSubject(final UserEntity user){
        return "fancake 비밀번호 찾기 초기화 비밀번호" ;
    }
    private String emailContent(final UserEntity user, String password){
        return "초기화 비밀번호 " + password ;
    }
    
    public void sendSimpleMessage( String to, String subject, String text) {
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("psj2867@naver.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        
    }
    
    
}
