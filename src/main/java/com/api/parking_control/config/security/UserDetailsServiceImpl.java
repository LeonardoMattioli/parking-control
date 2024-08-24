package com.api.parking_control.config.security;

import com.api.parking_control.models.UserModel;
import com.api.parking_control.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found" + username));
        return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true, userModel.getAuthorities());
    }
}
