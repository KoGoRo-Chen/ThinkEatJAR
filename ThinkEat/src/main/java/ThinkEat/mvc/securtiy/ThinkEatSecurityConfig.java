package ThinkEat.mvc.securtiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ThinkEatSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{

        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/ThinkEat/Account/AccountCenter").hasRole("Standard_User")
                        .anyRequest().authenticated()
        )
        .formLogin(form ->
                form
                        .loginPage("/ThinkEat/Account/LoginPage")
                        .loginProcessingUrl("/authenticateUser")
                        .permitAll()
        )
        .logout(logout -> logout.permitAll()
        )
        .exceptionHandling(configurer ->
                configurer.accessDeniedPage("ThinkEat/Account/AccessDenied")
        );

        return httpSecurity.build();
    }

    //InMemoryUser
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("Standard_User")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("Standard_User", "Admin")
                .build();

        return new InMemoryUserDetailsManager(john, mary);
    }
}
