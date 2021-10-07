package com.cafein.configuration.security;

import com.cafein.dao.UserRepository;
import com.cafein.dto.common.UserDetail;
import com.cafein.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndStatus(email, "ACTIVATE").orElse(null);
        if (user == null) {
            return null;
        }
        return UserDetail.builder().id(user.getId()).user(user).build();
    }

}