package me.kzv.jwtexam.security.auth;

import lombok.RequiredArgsConstructor;
import me.kzv.jwtexam.domain.Account;
import me.kzv.jwtexam.domain.AccountRepository;
import me.kzv.jwtexam.security.CustomUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("이메일 또는 비밀번호를 확인해주세요");
        });

        if (account.getSocialType() != null) {
            /**
             * 로컬 회원이 아닌 소셜로 가입한 회원
             * 정책에 따라
             * 1. 소셜 회원도 회원 가입시에 비밀번호를 입력 받아 양쪽 로그인을 이용 가능하게 한다.
             * 2. 소셜 회원으로 등록한 이메일은 일반로그인으로 로그인 할 수 없게 분리한다.
             *
             * 로직 설계하면 된다.
             * 일단 여기서는 2번 정책으로 진행한다.
             */
            throw new UsernameNotFoundException("이메일 또는 비밀번호를 확인해주세요");
        }


        return CustomUserPrincipal.of(account);
    }

}
