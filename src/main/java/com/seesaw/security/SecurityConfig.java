package com.seesaw.security;

import com.seesaw.authentication.AuthenticationResponse;
import com.seesaw.configuration.JwtAuthenticationFilter;
import com.seesaw.model.CustomOAuth2User;
import com.seesaw.model.Role;
import com.seesaw.service.CustomOAuth2UserService;
import com.seesaw.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private UserService userService;

    private final String[] routes = new String[]{
            "/",
            "/auth/**",
            "/email",
            "/admin/**",
            "/api/user/**",
            "/user/**",
            "/products/**",
            "/collections/**",
            "/cart/**",
            "/feedbacks/**",
            "/categories/**",
            "/cart-detail/**",
            "/contact/**",
            "/invoices/**",
            "/about/**",
            "/search/**",
            "/product-detail/**",
            "/admin/**",
            "/admin",
            "/orders/**",
            "/cart",
            "/categories/**",
            "/products",
            "/contact",
            "/about",
            "/search",
            "/search/**",
            "/product-detail",
            "/admin/**",
            "/admin",
            "/order",
            "product-detail/**",
            "error/**",
            "/payment",
            "/order/**",
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/category/**",
            "/collection/**",
            "/category",
            "/collection",
            "/order",
            "/order/**"

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> {
                            authorize
                                    .requestMatchers(routes)
                                    .permitAll()
                                    .requestMatchers(new String[]{
                                            "/api/v1/admin/**"
                                    }).hasRole(Role.ADMIN.name())
                                    .requestMatchers(
                                            "/admin/**"
                                    ).hasRole(Role.ADMIN.name())
                                    .anyRequest().authenticated();
                        }
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/auth/login")
                                .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauthUserService)
                        )
                        .successHandler(new AuthenticationSuccessHandler() {
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {

                                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                                if (userService.isExistUser(oauthUser.getEmail())) {
                                    userService.processOAuthPostLogin(oauthUser.getEmail(), response);
                                    response.sendRedirect("/user/login-google-again?email=" + oauthUser.getEmail());
                                } else {
                                    userService.processOAuthPostRegister(oauthUser.getEmail());
                                    response.sendRedirect("/user/login-google-success?email=" + oauthUser.getEmail());
                                }
                            }
                        })
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                );
        return http.build();
    }
}