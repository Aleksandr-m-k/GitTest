package ru.kata.spring.boot_security.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class Role { //implements GrantedAuthority, UserDetails {
//    private final User user;
//
//    @Autowired
//    public Role(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public String getAuthority() {
//        return null;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() { // аккаунт не просрочен
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {// аккаунт не заблокирован
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {// пароль не просрочен
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() { // аккаунт работает
//        return true;
//    }
}
