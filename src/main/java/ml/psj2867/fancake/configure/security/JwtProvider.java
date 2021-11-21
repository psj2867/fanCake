package ml.psj2867.fancake.configure.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.configure.ConfigureProperties;
import ml.psj2867.fancake.util.CookieUtil;

@Slf4j
@Component
public class JwtProvider {
    
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // public static final String BEARER_PREFIX = "Bearer";
    public static final String AUTHORIZATION_QUERY = "authToken";
    public static final String AUTHORIZATION_COOKIE = JwtProvider.AUTHORIZATION_HEADER;

    private static final String AUTHORITIES_KEY = "grants";
    private static final String USER_NAME_KEY = "user_name";
    private static final String USER_ID_KEY = "user_id";
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24; // 1일 
    public static final long TEMP_ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 ; // 1분
    //private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private final Key key;

    private JwtParser jwtParser;

    public JwtProvider(ConfigureProperties properties) {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getJwt().getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }
    public Cookie createCookie(TokenDto token) {
        return CookieUtil.createCookie(AUTHORIZATION_HEADER, token.getAccessToken());
    }
    public TokenDto generateTokenDto(Authentication authentication) {
        return generateTokenDto(authentication, ACCESS_TOKEN_EXPIRE_TIME);
    }
    public TokenDto generateTempTokenDtoForStomp(Authentication authentication) {
        return generateTokenDto(authentication, TEMP_ACCESS_TOKEN_EXPIRE_TIME);
    }
    private TokenDto generateTokenDto(Authentication authentication, long expireTime) {
        // 권한들 가져오기
        List<String> authoritieList = authentication.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.toList());
        String authorities = String.join(",", authoritieList);                                    

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + expireTime);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "IDX"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .claim(USER_NAME_KEY, authentication.getCredentials() )        // payload "user_name": "[name]"
                .claim(USER_ID_KEY, authentication.getDetails() )        // payload "user_name": "[name]"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        return TokenDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        if(claims == null) return null;

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                    Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), claims.get(USER_NAME_KEY), authorities);
    }

    private Claims parseClaims(String accessToken) {
        if(! StringUtils.hasLength(accessToken)){
            log.info("빈 토큰입니다.");
            return null;
        }
        try {
            Claims claims =  jwtParser.parseClaimsJws(accessToken).getBody();
            if (claims.getSubject() == null) {
                log.info("권한 정보가 없는 토큰입니다.");
                throw new RuntimeException();
            }
            if (claims.get(AUTHORITIES_KEY) == null || ! StringUtils.hasLength(claims.get(AUTHORITIES_KEY).toString())) {
                log.info("권한 정보가 없는 토큰입니다.");
                throw new RuntimeException();
            }
            if (claims.get(USER_NAME_KEY) == null) {
                log.info("사용자 정보가 없는 토큰입니다.");
                throw new RuntimeException();
            }
            return claims;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (RuntimeException e){
            return null;
        }
        return null;
    }
}