package ml.psj2867.fancake.configure.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ml.psj2867.fancake.util.CookieUtil;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider){
        super();
        this.jwtProvider = jwtProvider;
    }

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
        String paramterToken = request.getParameter(JwtProvider.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(paramterToken)) {
            return paramterToken;
        }
        String headerBearerToken = request.getHeader(JwtProvider.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(headerBearerToken)) {
            return headerBearerToken;
        }
        Cookie cookie =  CookieUtil.getCookie(request, JwtProvider.AUTHORIZATION_COOKIE);
        String cookieBearerToken = cookie == null ? null : cookie.getValue();
        if (StringUtils.hasText(cookieBearerToken)) {
            return cookieBearerToken;
        }        
        return null;
    }
}