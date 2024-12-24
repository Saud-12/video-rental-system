package com.crio.video_rental_system.service;

import com.crio.video_rental_system.controller.exchanges.AuthRequest;
import com.crio.video_rental_system.controller.exchanges.AuthResponse;
import com.crio.video_rental_system.controller.exchanges.RegisterRequest;
import com.crio.video_rental_system.entity.User;
import com.crio.video_rental_system.enums.Role;
import com.crio.video_rental_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){
        if(request.getRole()==null){
            request.setRole(Role.CUSTOMER);
        }

        User user=User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String jwtToken=jwtService.generateToken(user);
        userRepository.save(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponse login(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user=userRepository.findByEmail(request.getEmail());
        String jwtToken=jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
