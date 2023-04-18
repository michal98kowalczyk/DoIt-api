package com.example.doitapi.service;

import com.example.doitapi.payload.response.*;
import com.example.doitapi.payload.request.*;
import com.example.doitapi.model.*;
import com.example.doitapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        System.out.println("#1 MK Request: " + request);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Timestamp.valueOf(now) "+Timestamp.valueOf(now));

        var user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdDate(Timestamp.valueOf(now))
                .build();

        User savedUser = null;
        var jwtToken = "";
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            savedUser = repository.save(user);
            jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(savedUser !=null ? savedUser.getId() : null)
                .userEmail(savedUser !=null ? savedUser.getEmail() : null)
                .success(isSuccess)
                .errorMessage(errorMessage)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println("#1 MK AuthenticationRequest " + request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .userEmail(user.getEmail())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public User getUser(Long id) {
        return  repository.findById(id).get();
    }

    public AuthenticationResponse getAuthenticationResponse(User user) {
        return AuthenticationResponse.builder().userId(user.getId()).userEmail(user.getEmail()).build();
    }
}