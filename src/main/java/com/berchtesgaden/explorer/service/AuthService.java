package com.berchtesgaden.explorer.service;

import com.berchtesgaden.explorer.domain.Role;
import com.berchtesgaden.explorer.domain.User;
import com.berchtesgaden.explorer.dto.LoginRequest;
import com.berchtesgaden.explorer.dto.RegisterRequest;
import com.berchtesgaden.explorer.exception.UserAlreadyExistsException;
import com.berchtesgaden.explorer.repository.UserRepository;
import com.berchtesgaden.explorer.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        // 1. Проверяем что username не занят
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username", request.getUsername());
        }

        // 2.Checking that email not occupied

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email", request.getEmail());
        }

        // 3.Creating user

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

        return jwtService.generateToken(user.getUsername(), user.getRole().name());
    }

    public String login(LoginRequest request) {
        //1.Checking Security username + password

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Если дошли сюда — credentials верные
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        // 3. Generate and returning a token
        return jwtService.generateToken(user.getUsername(), user.getRole().name());
    }

    public String registerAdmin(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)  // ← ADMIN роль
                .build();

        userRepository.save(user);
        return jwtService.generateToken(user.getUsername(), user.getRole().name());
    }
}
