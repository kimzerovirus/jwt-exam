package me.kzv.jwtexam.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtils {

    public Cookie createNormalCookie(String key, String value, int maxAge){
        return create(key, value, maxAge,false );
    }

    public Cookie createHttpOnlyCookie(String key, String value, int maxAge){
        return create(key, value, maxAge,true );
    }

    public String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private Cookie create(String key, String value, int maxAge, boolean isHttpOnly) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);

        return cookie;
    }
}
