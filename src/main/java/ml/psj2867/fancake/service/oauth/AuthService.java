package ml.psj2867.fancake.service.oauth;

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
import java.util.Map;
import java.util.Optional;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.configure.ConfigureProperties;
import ml.psj2867.fancake.configure.security.JwtProvider;
import ml.psj2867.fancake.configure.security.SignOnTokenDto;
import ml.psj2867.fancake.configure.security.TokenDto;
import ml.psj2867.fancake.dao.UserEntityDao;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.exception.inner.NotMatchedCredentialException;
import ml.psj2867.fancake.exception.servererror.ServerErrorException;
import ml.psj2867.fancake.exception.unauth.LoginException;
import ml.psj2867.fancake.exception.unauth.UnAuthorizedException;
import ml.psj2867.fancake.service.oauth.model.LoginTypeEnum;
import ml.psj2867.fancake.service.oauth.model.NaverOAuthForm;
import ml.psj2867.fancake.service.oauth.model.NaverOAuthResponse;
import ml.psj2867.fancake.service.oauth.model.NaverOAuthUserInfo;
import ml.psj2867.fancake.service.oauth.model.NaverToken;
import ml.psj2867.fancake.service.user.UserService;
import ml.psj2867.fancake.service.user.model.UserLoginForm;
import ml.psj2867.fancake.util.GeneralUtil;
import ml.psj2867.fancake.util.OptionalUtil;

@Slf4j
@Service
public class AuthService {
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

    public TokenDto loginNaverCallBack(NaverOAuthForm naverOAuthForm) {
        NaverToken naverToken = getNaverAccessToekn(naverOAuthForm);
        return loginNaverUserOrAdd(naverToken);
    }
    public NaverToken getNaverAccessToekn(NaverOAuthForm naverOAuthForm) {
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
            Map<String, String> tokenInfo = GeneralUtil.convertJson2Map(response.body());
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
    public TokenDto loginNaverUserOrAdd(NaverToken accessToken) {
        NaverOAuthUserInfo naverUserInfo= getNaverUserInfo(accessToken).getResponse();        
        Optional<UserEntity> userEntity = userDao.findByIdIsAndLoginTypeIs(naverUserInfo.getId(), LoginTypeEnum.NAVER);        
        if(userEntity.isPresent()){
            return this.login(userEntity.get());
        }else{
            TokenDto token = this.login(userService.addNaverUser(naverUserInfo.convertToEntity()));   
            return SignOnTokenDto.of(token);
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
            NaverOAuthResponse parsedResponse = GeneralUtil.convertJson(userInfoJson, NaverOAuthResponse.class)
                            .orElseThrow(() -> new RuntimeException("json parsing error"));
            if( ! "00".equals(parsedResponse.getResultcode()) )
                throw new UnAuthorizedException();
            return parsedResponse;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.info("make uri error",e);
            throw new RuntimeException(e);
        }
    }

    public boolean isValidPassword(UserEntity userEntity, String rawPassword){
        return userEntity.isValidPassword(rawPassword,passwordEncoder);
    }

    public URI getNaverOAuthUri(String callBackUrl) {
        try {
            URI uri = new URIBuilder("https://nid.naver.com/oauth2.0/authorize").addParameter("response_type", "code")
                    .addParameter("client_id", properties.getNaver().getClient_id())
                    .addParameter("redirect_uri", callBackUrl)
                    .addParameter("state", new BigInteger(130, new SecureRandom()).toString()).build();
            return uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}