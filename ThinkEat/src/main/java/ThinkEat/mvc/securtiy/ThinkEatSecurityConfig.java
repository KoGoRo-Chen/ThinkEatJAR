package ThinkEat.mvc.securtiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ThinkEatSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{

        httpSecurity.authorizeHttpRequests(
                        configurer -> configurer
                                .requestMatchers("/Account/*").authenticated()
                                .requestMatchers("/ShareEat/*").authenticated()
                                .anyRequest().permitAll()
        )
        .formLogin(form ->
                form
                        .loginPage("/ThinkEat/Login")
                        .loginProcessingUrl("/ThinkEat/authenticateUser")
                        .permitAll()
        )
        .logout(logout -> logout.permitAll()
        )
        .exceptionHandling(configurer ->
                configurer.accessDeniedPage("/ThinkEat/AccessDenied")
        );

        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("Standard_User")
                .build();


        return new InMemoryUserDetailsManager(john);
    }
}
