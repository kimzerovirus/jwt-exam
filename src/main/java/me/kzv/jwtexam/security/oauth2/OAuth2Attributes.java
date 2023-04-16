package me.kzv.jwtexam.security.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kzv.jwtexam.domain.Account;
import me.kzv.jwtexam.domain.AuthorityType;
import me.kzv.jwtexam.domain.SocialType;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String email;
    private String profileImage;
    private SocialType socialType;


    public Account toEntity(){
        return Account.builder()
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .socialType(socialType)
                .authority(AuthorityType.ROLE_USER)
                .build();
    }

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return switch (SocialType.findByRegistrationId(registrationId)) {
            case GOOGLE -> ofGoogle(userNameAttributeName, attributes);
            case NAVER -> ofNaver("id", attributes);
            case KAKAO -> ofKakao("id", attributes);
        };
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileImage((String) attributes.get("picture"))
                .socialType(SocialType.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attributes.builder()
                .nickname((String) response.get("name"))
                .email((String) response.get("email"))
                .profileImage((String) response.get("profile_image"))
                .socialType(SocialType.NAVER)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> responseImage = (Map<String, Object>) response.get("profile");

        return OAuth2Attributes.builder()
                .nickname((String) response.get("nickname"))
                .email((String) response.get("email"))
                .profileImage((String) responseImage.get("profile_image_url"))
                .socialType(SocialType.KAKAO)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

}
