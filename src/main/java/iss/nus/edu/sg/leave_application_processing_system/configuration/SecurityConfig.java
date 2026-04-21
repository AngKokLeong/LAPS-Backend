package iss.nus.edu.sg.leave_application_processing_system.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import iss.nus.edu.sg.leave_application_processing_system.security.ApplicationUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/authenticate/**", "/h2-console/**", "/css/**", "/js/**", "/images/**",
                                "/webjars/**", "/actuator/health")
                        .permitAll()
                        .requestMatchers("/api/compensation/**").hasAnyRole("STAFF", "MANAGER", "ADMIN")
                        .requestMatchers("/staff/**").hasAnyRole("STAFF", "MANAGER", "ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/authenticate/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/authenticate/logout")
                        .logoutSuccessUrl("/?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                Object principal = authentication.getPrincipal();

                if (principal instanceof ApplicationUserDetails userDetails) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userRole", userDetails.getEmployee().getRole().name());
                    session.setAttribute("id", userDetails.getEmployee().getId());
                }

                if (authentication.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()))) {
                    response.sendRedirect("/admin/employee-management");
                    return;
                }

                if (authentication.getAuthorities().stream().anyMatch(a -> "ROLE_MANAGER".equals(a.getAuthority()))) {
                    response.sendRedirect("/manager/team-leave-history");
                    return;
                }

                response.sendRedirect("/staff");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
