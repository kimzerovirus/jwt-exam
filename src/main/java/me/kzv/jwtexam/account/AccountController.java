package me.kzv.jwtexam.account;

import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.kzv.jwtexam.account.dtos.LoginRequestDto;
import me.kzv.jwtexam.security.CustomUserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class AccountController {
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto dto) throws ServletException {
        log.info("signin attempted");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-account")
    public ResponseEntity getAccountInfo(Authentication authentication,  @AuthenticationPrincipal CustomUserPrincipal customUserPrincipal){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(authentication.getPrincipal());
        return ResponseEntity.ok().body(customUserPrincipal.toString());
    }
}
