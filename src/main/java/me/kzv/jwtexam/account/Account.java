package me.kzv.jwtexam.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kzv.jwtexam.account.enums.AuthorityType;
import me.kzv.jwtexam.account.enums.SocialType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private AuthorityType authority;
}
