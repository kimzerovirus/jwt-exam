package me.kzv.jwtexam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;

@Slf4j
@RestController
public class TestController {
    @GetMapping("/get-account")
    public ResponseEntity getAccountInfo(){
        return ResponseEntity.ok().body(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
