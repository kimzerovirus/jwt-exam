package me.kzv.jwtexam.account.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Email private String username;
    @NotBlank private String password;
}
