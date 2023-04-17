package me.kzv.jwtexam;

import lombok.RequiredArgsConstructor;
import me.kzv.jwtexam.account.Account;
import me.kzv.jwtexam.account.AccountRepository;
import me.kzv.jwtexam.account.enums.AuthorityType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
public class JwtExamApplication implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(JwtExamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String email = "test@test.com";
        accountRepository.findByEmail(email)
                .orElse(accountRepository.save(
                        Account.builder()
                                .email(email)
                                .password(passwordEncoder.encode("1234"))
                                .nickname("unknown123")
                                .authority(AuthorityType.ROLE_USER)
                                .build()
                ));
    }
}
