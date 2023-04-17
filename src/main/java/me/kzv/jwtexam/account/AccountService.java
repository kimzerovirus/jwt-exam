package me.kzv.jwtexam.account;

import lombok.RequiredArgsConstructor;
import me.kzv.jwtexam.security.CustomUserPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public CustomUserPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("존재하지 않는 이메일");
        });

        return CustomUserPrincipal.of(account);
    }
}
