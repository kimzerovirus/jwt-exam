package me.kzv.jwtexam.account.enums;

import java.util.Arrays;

public enum SocialType {
    GOOGLE, NAVER, KAKAO
    ;

    public static SocialType findByRegistrationId(String registrationId) {
        return Arrays.stream(SocialType.values())
                .filter(socialType -> socialType.toString().equals(registrationId.toUpperCase()))
                .findAny() // Multi thread에서 Stream을 처리할 때 가장 먼저 찾은 요소를 리턴한다.
                .orElseThrow(() -> {
                    throw new IllegalStateException("지원하지 않는 소셜 플랫폼입니다");
                });
    }
}

// http://www.tcpschool.com/java/java_api_enum