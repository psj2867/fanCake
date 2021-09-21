package ml.psj2867.demo;

import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ml.psj2867.demo.service.user.model.NaverOAuthResponse;

public class Test {
    public static void main(String[] args) throws Exception {
        String json = 
        "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"-moTX_e6zrrDTyGfbguQy_bn5FuQhbpKTbb8bLm2jp4\",\"email\":\"psj2867@naver.com\",\"mobile\":\"010-2231-2867\",\"mobile_e164\":\"+821022312867\",\"name\":\"\ubc15\uc120\uc7ac\"}}";

        System.out.println( convertJson(json,NaverOAuthResponse.class) );
        
    }
    private static <T> T convertJson(String json,Class<T> clazz ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        T result = null;
        try { 
            result = mapper.readValue(json,clazz); 
            return result;
        } catch (IOException e) {
          throw e;
        } 
    }
}