package ml.psj2867.fancake.service.security.model;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface WithEncodedPassword{
    void encode(PasswordEncoder passwordEncoder);
}