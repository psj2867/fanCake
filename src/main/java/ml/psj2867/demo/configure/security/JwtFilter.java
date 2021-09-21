package ml.psj2867.demo.configure.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String jwt = resolveToken(request);

        if (jwtProvider.validateToken(jwt)) {
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        

        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String headerBearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(headerBearerToken) && headerBearerToken.startsWith(BEARER_PREFIX)) {
            return headerBearerToken;
        }
        String cookieBearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(cookieBearerToken) && cookieBearerToken.startsWith(BEARER_PREFIX)) {
            return cookieBearerToken;
        }        
        return null;
    }
}