package me.kzv.jwtexam.config;

import lombok.RequiredArgsConstructor;
import me.kzv.jwtexam.security.auth.CustomAuthenticationProvider;
import me.kzv.jwtexam.security.auth.CustomUserDetailsService;
import me.kzv.jwtexam.security.jwt.JwtAccessDeniedHandler;
import me.kzv.jwtexam.security.jwt.JwtAuthenticationEntryPoint;
import me.kzv.jwtexam.security.jwt.JwtAuthenticationFilter;
import me.kzv.jwtexam.security.handler.CustomLoginFailureHandler;
import me.kzv.jwtexam.security.handler.CustomLoginSuccessHandler;
import me.kzv.jwtexam.security.oauth2.CustomOAuth2UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/get-account").authenticated();
                    auth.anyRequest().permitAll();
                })

                .formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .permitAll()

                .and()
                .userDetailsService(customUserDetailsService)
                .authenticationProvider(customAuthenticationProvider)

                .oauth2Login(
                        oauth -> {
                            oauth.loginPage("/login");
                            oauth.userInfoEndpoint().userService(customOAuth2UserService);
                            oauth.successHandler(customLoginSuccessHandler);
                            oauth.failureHandler(customLoginFailureHandler);
                        }
                )

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
//        return authenticationManagerBuilder.build();
//    }
}
