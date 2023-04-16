package me.kzv.jwtexam.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.kzv.jwtexam.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class CustomUserPrincipal implements UserDetails, OAuth2User {
    private Account account;
    private Map<String, Object> attributes;

    // 일반 로그인
    public static CustomUserPrincipal of(Account member) {
        return CustomUserPrincipal.of(member, Map.of());
    }

    // oauth2 로그인
    public static CustomUserPrincipal of(Account account, Map<String, Object> attributes) {
        return new CustomUserPrincipal(account, attributes);
    }

    @Override
    public String getName() {
        // Account 의 PK 반환
        return account.getId().toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(account.getAuthority().toString()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }




    // 구체적인 룰이 없으므로 단순히 true 를 리턴해 준다.
    @Override public boolean isAccountNonExpired() {
        return true;
    }
    @Override public boolean isAccountNonLocked() {
        return true;
    }
    @Override public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override public boolean isEnabled() {
        return true;
    }
}

/**
 *  isAccountNonExpired(): 계정이 만료되지 않았는지를 의미(true 리턴시 만료되지 않음을 의미)
 *
 *  isAccountNonLocked(): 계정이 잠겨있지 않은지를 의미(true 리턴시 계정이 잠겨있지 않음을 의미)
 *
 *  isCredentialsNonExpired(): 계정의 패스워드가 만료되지 않았는지를 의미(true 리턴시 패스워드가 만료되지 않음을 의미)
 *
 *  isEnabled(): 계정이 사용가능한 계정인지를 의미(true 리턴시 사용가능한 계정인지를 의미)
 *
 *  getAuthorities(): 계정이 갖고 있는 권한 목록
 */
