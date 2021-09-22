package ml.psj2867.demo.service.user;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.demo.configure.ConfigureProperties;
import ml.psj2867.demo.configure.security.JwtFilter;
import ml.psj2867.demo.configure.security.JwtProvider;
import ml.psj2867.demo.configure.security.TokenDto;
import ml.psj2867.demo.dao.UserEntityDao;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.exception.LoginException;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;
import ml.psj2867.demo.service.user.model.NaverOAuthForm;
import ml.psj2867.demo.service.user.model.NaverOAuthResponse;
import ml.psj2867.demo.service.user.model.NaverOAuthUserInfo;
import ml.psj2867.demo.service.user.model.NaverToken;
import ml.psj2867.demo.service.user.model.UserForm;
import ml.psj2867.demo.util.CookieUtil;
import ml.psj2867.demo.util.OptionalUtil;

@Slf4j
@Service
public class AuthService {
    private final String NAVER_CALL_BACK_URI = "http://34.70.43.54/login/naver/callback";

    @Autowired
    private UserEntityDao userDao;

    @Autowired
    private ConfigureProperties properties;
    @Autowired
    private JwtProvider jwtProvider;

    public void login(UserEntity user, HttpServletResponse res) throws LoginException {
        try {
            Authentication authUser = new UsernamePasswordAuthenticationToken(user.getId(), user.getPasswd());
            final TokenDto token = jwtProvider.generateTokenDto(authUser);
            res.addHeader(JwtFilter.AUTHORIZATION_HEADER, token.getAccessToken());
            res.addCookie(CookieUtil.createCookie(JwtFilter.AUTHORIZATION_HEADER, token.getAccessToken()));
        } catch (Exception e) {
            throw new LoginException(e);
        }
    }

    public UserEntity loginOriginUser(UserForm userForm) {
        Optional<UserEntity> userEntity = userDao.findByIdIsAndLoginTypeIs(userForm.getId(), LoginTypeEnum.ORIGIN);
        return userEntity
            .orElseThrow( ()->new LoginException() );
    }

    public UserEntity loginNaverUserOrAdd(NaverOAuthForm naverOAuthForm) {
        final NaverToken accessToken = getNaverAccessToekn(naverOAuthForm);
        NaverOAuthUserInfo userInfo= getNaverUserInfo(accessToken).getResponse();        
        Optional<UserEntity> userEntity = userDao.findByIdIsAndLoginTypeIs(userInfo.getId(), LoginTypeEnum.NAVER);        
        return userEntity
            .orElseGet(()->{
                UserEntity newNaverUser = UserEntity.builder()
                                            .id(userInfo.getId())
                                            .name(userInfo.getName())
                                            .loginType(LoginTypeEnum.NAVER)
                                            .build();
                userDao.save(newNaverUser);
                return newNaverUser;
        });
    }
    private NaverToken getNaverAccessToekn(NaverOAuthForm naverOAuthForm) {
        try {
            HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
            URI uri = new URIBuilder("https://nid.naver.com/oauth2.0/token")
                    .addParameter("grant_type", "authorization_code")
                    .addParameter("client_id", properties.getNaver().getClient_id())
                    .addParameter("client_secret", properties.getNaver().getClient_secret())
                    .addParameter("code", naverOAuthForm.getCode()).addParameter("state", naverOAuthForm.getState())
                    .build();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            Map<String, String> tokenInfo = convertJson2Map(response.body());
            NaverToken token = NaverToken.builder()
                                .access_token(tokenInfo.get("access_token"))
                                .refresh_token(tokenInfo.get("refresh_token"))
                                .token_type(tokenInfo.get("token_type"))
                                .expires_in(OptionalUtil.paseLong(tokenInfo.get("expires_in"))
                                    .orElseThrow(()->new RuntimeException("naver token parsing error : expires_in"))
                                    )
                                .build();
            return token;
        } catch (URISyntaxException | IOException | InterruptedException  e) {
            log.debug("",e);
            throw new LoginException(e);
        }
    }

    private NaverOAuthResponse getNaverUserInfo(final NaverToken accessToken) {
        HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
        URI uri;
        try {
            uri = new URIBuilder("https://openapi.naver.com/v1/nid/me").build();
            String header = "Bearer " + accessToken.getAccess_token();
            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(uri)
                                    .header("Authorization", header)
                                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String userInfoJson = response.body();
            return convertJson(userInfoJson, NaverOAuthResponse.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.info("make uri error",e);
            throw new RuntimeException(e);
        }
    }


    public URI getNaverOAuthUri() {
        try {
            URI uri = new URIBuilder("https://nid.naver.com/oauth2.0/authorize").addParameter("response_type", "code")
                    .addParameter("client_id", properties.getNaver().getClient_id())
                    .addParameter("redirect_uri", NAVER_CALL_BACK_URI)
                    .addParameter("state", new BigInteger(130, new SecureRandom()).toString()).build();
            return uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T convertJson(String json,Class<T> clazz ){  
        ObjectMapper mapper = new ObjectMapper();
        T result = null;
        try { 
            result = mapper.readValue(json,clazz); 
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{                  
            log.debug("convert json : {} - {}" , json, result);
        }
    }
    
    private Map<String,String> convertJson2Map(String json){  
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;
        try { 
            map = mapper.readValue(json, new TypeReference<Map<String, String>>() {}); 
            return map;
        } catch (IOException e) {
            return new HashMap<>();
        } finally{                  
            log.debug("convert json : {} - {}" , json, map);
        }
    }
}