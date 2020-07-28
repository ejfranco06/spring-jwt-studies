package dev.emiliofranco.springjwtstudies.service;

import dev.emiliofranco.springjwtstudies.model.AppUser;
import dev.emiliofranco.springjwtstudies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
        return new User(user.getUserName(), user.getPassword(), Collections.emptyList());
    }
}
