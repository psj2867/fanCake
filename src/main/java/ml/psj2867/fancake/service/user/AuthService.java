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
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import ml.psj2867.fancake.service.user.model.UserLoginForm;
import ml.psj2867.fancake.service.user.model.auth.LoginTypeEnum;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthResponse;
import ml.psj2867.fancake.service.user.model.auth.NaverOAuthUserInfo;
import ml.psj2867.fancake.service.user.model.auth.NaverToken;

@Slf4j
@Service
public class AuthService {
    private final String NAVER_CALL_BACK_URI = "http://34.70.43.54/login/naver/callback";

    @Autowired
    private UserEntityDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public TokenDto loginOriginUser(UserLoginForm userForm)throws LoginException  {
        Optional<UserEntity> nullableUserEntity = userDao.findByIdIsAndLoginTypeIs(userForm.getId(), LoginTypeEnum.ORIGIN);
        UserEntity userEntity = nullableUserEntity
                        .orElseThrow( ()->new NotMatchedCredentialException() );
        if(! this.isValidPassword(userEntity, userForm.getPassword()) )
            throw new NotMatchedCredentialException();
        return this.login(userEntity);
    }


    public TokenDto loginNaverUserOrAdd(NaverToken accessToken) {
        NaverOAuthUserInfo naverUserInfo= getNaverUserInfo(accessToken).getResponse();        
        Optional<UserEntity> userEntity = userDao.findByIdIsAndLoginTypeIs(naverUserInfo.getId(), LoginTypeEnum.NAVER);        
        UserEntity user = userEntity
            .orElseGet(()->userService.addNaverUser(naverUserInfo.convertToEntity()));
        return this.login(user);
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

    public boolean isValidPassword(UserEntity userEntity, String rawPassword){
        return userEntity.isValidPassword(rawPassword,passwordEncoder);
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

}