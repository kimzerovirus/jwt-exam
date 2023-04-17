package me.kzv.jwtexam.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.kzv.jwtexam.account.Account;
import me.kzv.jwtexam.account.AccountRepository;
import me.kzv.jwtexam.security.CustomUserPrincipal;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AccountRepository accountRepository;

    @Override
    public CustomUserPrincipal loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인하는 소셜 플랫폼 id
        String socialPlatformId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attributes attributes = OAuth2Attributes.of(socialPlatformId, userNameAttributeName, oAuth2User.getAttributes());
        Account account = getAccount(attributes);

        return CustomUserPrincipal.of(account, attributes.getAttributes());
    }

    private Account getAccount(OAuth2Attributes attributes) {
        return accountRepository.findByEmail(attributes.getEmail()).orElseGet(() -> accountRepository.save(attributes.toEntity()));
    }
}
