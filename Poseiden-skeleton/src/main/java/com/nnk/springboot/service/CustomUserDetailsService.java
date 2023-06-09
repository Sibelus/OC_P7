package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    /**
     * Method that search a user by his username into the database
     * -> If he doesn't exist throw UsernameNotFoundException
     * -> If he exists return an object UserDetails that contain the user's username, password & role
     * @param username
     * @return @{@link UserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        final User customer = userRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = new org.springframework.security.core.userdetails.User
                (customer.getUsername(),
                customer.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(customer.getRole())));
        return user;
    }
}
