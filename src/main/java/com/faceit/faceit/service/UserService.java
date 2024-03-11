package com.faceit.faceit.service;

import com.faceit.faceit.security.User;
import com.faceit.faceit.dao.UserRepository;
import com.faceit.faceit.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(
                String.format("User '%s' not found",username)));
        return UserDetailsImpl.build(user);
    }
}
