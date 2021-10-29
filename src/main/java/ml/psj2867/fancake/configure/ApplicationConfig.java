package ml.psj2867.fancake.configure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ml.psj2867.fancake.configure.security.JwtProvider;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class ApplicationConfig implements WebMvcConfigurer{
    
    @Bean
    public Docket api(ConfigureProperties properties) {		
		//Authentication header 처리를 위해 사용
		List<RequestParameter> global = new ArrayList<>();
        global.add(new RequestParameterBuilder()
                        .name(JwtProvider.AUTHORIZATION_HEADER)
                        .description("Access Token")
                        .in("header")
                        .required(false)
                        .build());
        global.add(new RequestParameterBuilder()
                        .name(JwtProvider.AUTHORIZATION_QUERY)
                        .description("Access Token")
                        .in("query")
                        .required(false)
                        .build());
                                  
        return new Docket(DocumentationType.SWAGGER_2)
                .host(properties.getHost())
                .globalRequestParameters(global)
                .consumes(getConsumeContentTypes())
                .select()
                    .apis(RequestHandlerSelectors.basePackage("ml.psj2867.fancake.controller.api"))//Swagger API 문서로 만들기 원하는 basePackage 경로
                    .paths(PathSelectors.ant("/api/**"))    //URL 경로를 지정하여 해당 URL에 해당하는 요청만 SWAGGER로 만듦
                .build();
    }
    private Set<String> getConsumeContentTypes(){
        Set<String> consumes = new HashSet<>();
        // consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }
}