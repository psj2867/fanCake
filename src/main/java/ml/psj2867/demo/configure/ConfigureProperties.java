package ml.psj2867.demo.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.psj2867.demo.configure.properties.GoogleProperty;
import ml.psj2867.demo.configure.properties.JwtProperty;
import ml.psj2867.demo.configure.properties.NaverProperty;

@ConfigurationProperties(prefix="ml.psj2867")
@Component
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ConfigureProperties {

    private JwtProperty jwt;
    private NaverProperty naver; 
    private GoogleProperty google; 
    
}