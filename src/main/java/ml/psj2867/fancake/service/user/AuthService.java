package ml.psj2867.fancake.service.user;

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

import javax.management.RuntimeErrorException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.configure.ConfigureProperties;
import ml.psj2867.fancake.configure.security.JwtProvider;
import ml.psj2867.fancake.configure.security.TokenDto;
import ml.psj2867.fancake.dao.UserEntityDao;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.inner.NotMatchedCredentialException;
import ml.psj2867.fancake.exception.servererror.ServerErrorException;
import ml.psj2867.fancake.exception.unauth.LoginException;
import ml.psj2867.fancake.service.user.model.UserForm;
import ml.psj2867.fancake.service.user.model.auth.LoginTypeEnum;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthForm;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthResponse;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthUserInfo;
import ml.psj2867.fancake.service.user.model.auth.NaverToken;
import ml.psj2867.fancake.util.OptionalUtil;

@Slf4j
@Service
public class AuthService {
    private final String NAVER_CALL_BACK_URI = "http://34.70.43.54/login/naver/callback";

    @Autowired
    private UserEntityDao userDao;
    @Autowired
    private UserService userService;

    @Autowired
    private ConfigureProperties properties;
    @Autowired
    private JwtProvider jwtProvider;

    private TokenDto login(UserEntity user) throws LoginException {
        try {
            UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(user.getIdx(), user.getName(), user.getGrants());
            final TokenDto token = jwtProvider.generateTokenDto(authUser);
            return token;
        } catch (Exception e) {
            throw new ServerErrorException(e);
        }
    }

    public TokenDto loginOriginUser(UserForm userForm)throws LoginException  {
        Optional<UserEntity> nullableUserEntity = userDao.findByIdIsAndLoginTypeIs(userForm.getId(), LoginTypeEnum.ORIGIN);
        UserEntity userEntitiy = nullableUserEntity
                        .orElseThrow( ()->new NotMatchedCredentialException() );
        return this.login(userEntitiy);
    }

    public TokenDto loginNaverUserOrAdd(NaverOAuthForm naverOAuthForm) {
        final NaverToken accessToken = getNaverAccessToekn(naverOAuthForm);
        NaverOAuthUserInfo naverUserInfo= getNaverUserInfo(accessToken).getResponse();        
        Optional<UserEntity> userEntity = userDao.findByIdIsAndLoginTypeIs(naverUserInfo.getId(), LoginTypeEnum.NAVER);        
        UserEntity user = userEntity
            .orElseGet(()->userService.addNaverUser(naverUserInfo.convertToEntity()));
        return this.login(user);
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
            if(tokenInfo.get("error") != null ) throw new RuntimeException(tokenInfo.get("error"));
            NaverToken token = NaverToken.builder()
                                .access_token(tokenInfo.get("access_token"))
                                .refresh_token(tokenInfo.get("refresh_token"))
                                .token_type(tokenInfo.get("token_type"))
                                .expires_in(OptionalUtil.parseLong(tokenInfo.get("expires_in"))
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