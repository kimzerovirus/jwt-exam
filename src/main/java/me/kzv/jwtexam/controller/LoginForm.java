package me.kzv.jwtexam.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {
    @Email private String username;
    @NotBlank private String password;
}
