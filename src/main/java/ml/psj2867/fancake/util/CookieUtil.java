package ml.psj2867.fancake.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import ml.psj2867.fancake.configure.security.JwtProvider;

public class CookieUtil {

    public static Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setMaxAge((int)JwtProvider.ACCESS_TOKEN_EXPIRE_TIME);
        token.setPath("/");
        return token;
    }

    public static Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

}