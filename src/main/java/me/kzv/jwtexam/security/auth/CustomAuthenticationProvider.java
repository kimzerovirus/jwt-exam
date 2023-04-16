package me.kzv.jwtexam.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kzv.jwtexam.security.CustomUserPrincipal;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials(); // 입력 받은 비밀번호

        CustomUserPrincipal userPrincipal = userDetailsService.loadUserByUsername(email);
        System.out.println(userPrincipal);

        if (!passwordEncoder.matches(password, userPrincipal.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호를 확인해주세요");
        }

        log.info(email);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        log.info(String.valueOf(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)));
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
        return true;
    }
}
