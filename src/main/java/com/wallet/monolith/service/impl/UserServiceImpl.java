package com.wallet.monolith.service.impl;

import com.wallet.monolith.configuration.jwt.JwtUtils;
import com.wallet.monolith.domain.dto.UserRequest;
import com.wallet.monolith.domain.dto.UserResponse;
import com.wallet.monolith.domain.mapper.UserRequestMapper;
import com.wallet.monolith.domain.mapper.UserResponseMapper;
import com.wallet.monolith.domain.model.User;
import com.wallet.monolith.repository.UserRepository;
import com.wallet.monolith.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserResponse getUserById(Integer id) throws Exception {
        User user;
        UserResponse userResponse;

        if (!userRepository.findById(id).isPresent()) {
            throw new Exception("User not found");
        }

        user = userRepository.findById(id).orElse(null);
        userResponse = userResponseMapper.userToUserResponse(user);

        return userResponse;
    }

    @Override
    public UserResponse loginUser(UserRequest userRequest) {
        User username;
        UserResponse userResponse;

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                ));

        username = getUserByUsername(userRequest.getUsername());
        userRequest.setJwt(jwtUtils.generateToken(userRequest.getUsername()));
        userRequest.setUserId(username.getUserId());

        userResponse = userResponseMapper.userRequestToUserResponse(userRequest);

        return userResponse;
    }

    @Override
    public void saveUser(UserRequest userRequest) throws Exception {
        String username = getUserByUsername(userRequest.getUsername()).getUsername();
        if (username.equals(userRequest.getUsername())){
            throw new Exception("User already exists");
        }

        User user = userRequestMapper.toUser(userRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usr = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(usr.getUsername(), usr.getPassword(), new ArrayList<>());
    }
}
