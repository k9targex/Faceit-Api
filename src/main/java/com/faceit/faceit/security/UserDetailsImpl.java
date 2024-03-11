package com.faceit.faceit.security;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
<<<<<<< HEAD
=======
import java.util.Collections;

>>>>>>> 94a697e9c58e9f9683ee4a97e4c9db455707a693
@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String password;
    private String username;
    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(
                user.getId(),
                user.getPassword(),
                user.getUsername());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
<<<<<<< HEAD
        return null;
    }
=======
        return Collections.emptyList();
    }

>>>>>>> 94a697e9c58e9f9683ee4a97e4c9db455707a693
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}