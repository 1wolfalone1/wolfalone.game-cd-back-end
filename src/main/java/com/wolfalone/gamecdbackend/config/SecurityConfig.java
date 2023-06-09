package com.wolfalone.gamecdbackend.config;


import com.wolfalone.gamecdbackend.filter.JwtAuthenticationFilter;
import com.wolfalone.gamecdbackend.service.iml.CustomOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    private static  final String[] WHITE_LIST_URLS = {
            "/api/v1/authentication/**",
            "/auth/**",
            "/oauth/**",
            "/oauth2",
            "/login",
            "/api/v1/games/**",
            "/api/v1/category/**",
            "/api/v1/category",
            "/"
    };

    private static  final String[] WHITE_LIST_USER_URLS = {
            "/api/v1/order/**",
            "/api/v1/order-details/**"
    };
    private static  final String[] WHITE_LIST_ADMIN_URLS = {
            "/api/v1/admin/**",
    };
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3006", "https://www.game-cd.store"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;
//    @Autowired
//    private Oauth2SuccessfulHandler oauth2SuccessfulHandler;
    @Autowired
    private CustomOauth2UserService customOauth2UserServie;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()

                .authorizeHttpRequests(
                    auth ->auth
                            .requestMatchers(WHITE_LIST_URLS)
                            .permitAll()
                            .requestMatchers(WHITE_LIST_USER_URLS)
                            .hasAnyRole("USER", "ADMIN")
                            .requestMatchers( WHITE_LIST_ADMIN_URLS)
                            .hasAnyRole("ADMIN")

                            .anyRequest()
                            .authenticated()
                )
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOauth2UserServie)
//                .and().successHandler(oauth2SuccessfulHandler)
//                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
