package ThinkEat.mvc.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final User user;

    public UserDetails(User user) {

        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 假設 User 類中有一個名為 getAuthority() 的方法返回權限
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getAuthority().getAuthority());
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    public String getRawPassword() {

        return user.getRawPassword();
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return !user.isTokenExpired();
    }

    @Override
    public boolean isAccountNonLocked() {

        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;  // Assuming credentials never expire
    }

    @Override
    public boolean isEnabled() {

        return user.isEnabled();
    }
}
