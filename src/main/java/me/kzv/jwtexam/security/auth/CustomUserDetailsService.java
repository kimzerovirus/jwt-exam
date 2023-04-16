package me.kzv.jwtexam.security.auth;

import lombok.RequiredArgsConstructor;
import me.kzv.jwtexam.domain.Account;
import me.kzv.jwtexam.domain.AccountRepository;
import me.kzv.jwtexam.security.CustomUserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public CustomUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("존재하지 않는 이메일"); // 이 에러로 던져야 스프링 시큐리티가 알아서 해결해줌
        });

        return CustomUserPrincipal.of(account);
    }

}
