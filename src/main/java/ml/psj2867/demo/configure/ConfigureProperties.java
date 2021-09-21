package ml.psj2867.demo.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import ml.psj2867.demo.configure.properties.JwtProperty;
import ml.psj2867.demo.configure.properties.NaverProperty;

@ConfigurationProperties(prefix="ml.psj2867")
@Component
@Data
public class ConfigureProperties {

    private JwtProperty jwt;
    private NaverProperty naver; 
    
}