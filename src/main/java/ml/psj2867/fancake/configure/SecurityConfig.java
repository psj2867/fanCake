package ml.psj2867.fancake.configure;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.configure.security.JwtFilter;
import ml.psj2867.fancake.configure.security.JwtProvider;
import ml.psj2867.fancake.util.MessageDto;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    private JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/login/**");

        web.httpFirewall(defaultHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll()
            .and()
                .csrf()
            .disable()
                .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .formLogin()
                .disable();
        http.addFilterAfter(new JwtFilter(this.jwtProvider), ExceptionTranslationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }
    private AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) ->{
            log.error("error in filter",authException);
            response.getOutputStream().print(MessageDto.of("unAtuh").toJson());
        };
    }


    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }


}