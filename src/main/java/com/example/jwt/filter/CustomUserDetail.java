package com.example.jwt.filter;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jwt.entity.Employee;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetail implements UserDetails {

    private final Employee employee;

    public CustomUserDetail(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can return roles/authorities if you have any
        return Collections.emptyList(); // or implement roles if needed
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getEmail(); // assuming email is used as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // change if you handle account expiry
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change if you handle locking
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change if you handle credential expiry
    }

    @Override
    public boolean isEnabled() {
        return true; // change if you handle account status
    }
}
