package com.practice.practice.serviceimple;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.practice.practice.repository.UserRepository;
import com.practice.practice.service.JwtTokenHelper;
import com.practice.practice.service.UserService;
import com.practice.practice.entities.Users;
import com.practice.practice.payloads.UserDto;

@Service
public class ServiceImple implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public UserDto createUser(UserDto userDto) {
        Users user = modelMapper.map(userDto, Users.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User Not Found !!");
        }
        return modelMapper.map(user, UserDto.class);
    }

    public String verify(UserDto userDto) {
        org.springframework.security.core.Authentication authenticationManager = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));

        if (authenticationManager.isAuthenticated()) {
            return jwtTokenHelper.generateToken(userDto.getEmail());
        }
        return "fail to login";

    }

}
