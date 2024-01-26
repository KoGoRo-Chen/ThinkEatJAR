package ThinkEat.mvc.securtiy;

import ThinkEat.mvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ThinkEatSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        httpSecurity
                .sessionManagement(configurer ->
                        configurer
                                .maximumSessions(1)
                                .expiredUrl("/ThinkEat/Login"))
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/Account/*").authenticated()
                                .requestMatchers("/ShareEat/*").authenticated()
                                .anyRequest().permitAll()
        )
        .formLogin(form ->
                form
                        .loginPage("/ThinkEat/Login")
                        .loginProcessingUrl("/ThinkEat/authenticateUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
        )
                .logout(logout ->
                        logout
                                .logoutUrl("/ThinkEat/logout")
                                .logoutSuccessUrl("/ThinkEat/Index")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/ThinkEat/logout", "GET"))
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .exceptionHandling((configurer ->
                        configurer.accessDeniedPage("/ThinkEat/access-denied"))

                );

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    // 認證管理器
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
