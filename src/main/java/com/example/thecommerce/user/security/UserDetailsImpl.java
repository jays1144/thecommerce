package com.example.thecommerce.user.security;


import com.example.thecommerce.user.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Slf4j(topic = "UserDetailsImpl")
@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user){
        log.info(user.getEmail());
        this.user = user;
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(null);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}