package me.kzv.jwtexam.security.oauth2;

import lombok.extern.log4j.Log4j2;

import me.kzv.jwtexam.security.jwt.JwtTokenProvider;
import me.kzv.jwtexam.security.jwt.TokenDto;
import me.kzv.jwtexam.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final String redirectUrl;
    private final CookieUtils cookieUtils;

    public CustomOAuth2SuccessHandler(JwtTokenProvider tokenProvider, @Value("${oauth2.authorized-redirect-url}") String redirectUrl, CookieUtils cookieUtils) {
        this.tokenProvider = tokenProvider;
        this.redirectUrl = redirectUrl;
        this.cookieUtils = cookieUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
            // 응답이 이미 커밋되었을 경우? -> 응답이 이미 완료되었는데 임의로 접근하는 경우??
            log.warn("Unable to redirect");
            return;
        }

        TokenDto token = tokenProvider.create(authentication);

        Cookie accessCookie = cookieUtils.createNormalCookie("ac_token", token.getAccessToken(), token.getAccessTokenExpiresIn().intValue());
        Cookie refreshCookie = cookieUtils.createHttpOnlyCookie("rf_token", token.getRefreshToken(), token.getRefreshTokenExpiresIn().intValue());

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        System.out.println("success handler----------------finish");
//        final String token = null;
//        final String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl).queryParam("token", token).build().toString();
        final String targetUrl = "http://localhost:8080";

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
