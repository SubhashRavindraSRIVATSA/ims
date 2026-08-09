package com.subhash.ims.service.impl;

import com.subhash.ims.dto.JwtResponse;
import com.subhash.ims.dto.LoginRequest;
import com.subhash.ims.dto.RegisterRequest;
import com.subhash.ims.dto.RegisterResponse;
import com.subhash.ims.entity.User;
import com.subhash.ims.repository.UserRepository;
import com.subhash.ims.security.JwtService;
import com.subhash.ims.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    //private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        log.info("Registering user {}", request.getEmail());

//        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
//
//            throw new ResourceAlreadyExistsException(
//                    "Email already exists : " + request.getEmail());
//
//        }
//
//        if (userRepository.existsByPhone(request.getPhone())) {
//
//            throw new ResourceAlreadyExistsException(
//                    "Phone already exists : " + request.getPhone());

        }

//        Role role = roleRepository.findByName(RoleType.CUSTOMER)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Default role CUSTOMER not found"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountLocked(false)
                //.role(role)
                .build();

        User savedUser = userRepository.save(user);

        log.info("User registered successfully : {}", savedUser.getId());

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().getName().name())
                .message("Registration successful.")
                .build();

    }

    @Override
    public JwtResponse login(LoginRequest request) {

        log.info("Authenticating {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        String jwt = jwtService.generateToken(
                new CustomUserPrincipal(user));

        return JwtResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName().name())
                .build();

    }
}
