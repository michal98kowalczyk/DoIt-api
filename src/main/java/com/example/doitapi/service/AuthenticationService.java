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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        LocalDateTime now = LocalDateTime.now();

        Optional<User> fromDb = repository.findByEmail(request.getEmail());
        if (fromDb.isPresent()){
            return AuthenticationResponse.builder()
                    .userEmail(request.getEmail())
                    .success(false)
                    .errorMessage("Email already exist!")
                    .build();
        }
        fromDb = repository.findByUsername(request.getUsername());
        if (fromDb.isPresent()){
            return AuthenticationResponse.builder()
                    .userEmail(request.getEmail())
                    .success(false)
                    .errorMessage("Username already exist!")
                    .build();
        }

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


        return getAuthenticationResponse(savedUser,jwtToken,errorMessage,isSuccess);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = null;
        var jwtToken = "";
        Boolean isSuccess  = true;
        String  errorMessage = "";
        try {
            System.out.println("#1 MK AuthenticationRequest " + request);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
        }catch (RuntimeException ex) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }
        return getAuthenticationResponse(user,jwtToken,errorMessage,isSuccess);
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

    private void deleteAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser(user);
        if (validUserTokens.isEmpty())
            return;
        tokenRepository.deleteAll(validUserTokens);
    }

    public User getUser(Long id) {
        return  repository.findById(id).get();
    }

    public AuthenticationResponse getAuthenticationResponse(User user) {
        return AuthenticationResponse.builder().userId(user.getId()).userEmail(user.getEmail()).build();
    }

    public AuthenticationResponse getAuthenticationResponse(User user,String jwtToken, String errorMessage, Boolean isSuccess ) {
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user !=null ? user.getId() : null)
                .userEmail(user !=null ? user.getEmail() : null)
                .username(user !=null ? user.getRealUsername() : null)
                .firstName(user !=null ? user.getFirstName() : null)
                .lastName(user !=null ? user.getLastName() : null)
                .info(user !=null ? user.getInfo() : null)
                .success(isSuccess)
                .errorMessage(errorMessage)
                .role(user !=null ? String.valueOf(user.getRole()) : null)
                .build();
    }

    public AuthenticationResponse updateUser(RegisterRequest request, String id) {
        LocalDateTime now = LocalDateTime.now();
        User userFromDB =  getUser(Long.valueOf(id));

        if(!userFromDB.getEmail().equals(request.getEmail())){
            return getAuthenticationResponse(null,null,"Wrong user!",false);
        }

        userFromDB.setFirstName(request.getFirstName());
        userFromDB.setLastName(request.getLastName());
        userFromDB.setInfo(request.getUserInfo());
        userFromDB.setLastModifiedDate(Timestamp.valueOf(now));


        User savedUser = null;
        var jwtToken = "";
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            savedUser = repository.save(userFromDB);
            jwtToken = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, jwtToken);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }

        return getAuthenticationResponse(savedUser,jwtToken,errorMessage,isSuccess);
    }

    public AuthenticationResponse changeEmail(RegisterRequest request, String id) {
        LocalDateTime now = LocalDateTime.now();
        User userFromDB =  getUser(Long.valueOf(id));

        if(!userFromDB.getRealUsername().equals(request.getUsername())){
            return getAuthenticationResponse(null,null,"Wrong user!",false);
        }
        userFromDB.setEmail(request.getEmail());
        userFromDB.setLastModifiedDate(Timestamp.valueOf(now));

        User savedUser = null;
        var jwtToken = "";
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            savedUser = repository.save(userFromDB);
            jwtToken = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, jwtToken);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }

        return getAuthenticationResponse(savedUser,jwtToken,errorMessage,isSuccess);
    }

    public AuthenticationResponse changePassword(RegisterRequest request, String id) {
        LocalDateTime now = LocalDateTime.now();
        User userFromDB =  getUser(Long.valueOf(id));

        if(!userFromDB.getRealUsername().equals(request.getUsername())){
            return getAuthenticationResponse(null,null,"Wrong user!",false);
        }
        if (!passwordEncoder.matches(request.getOldPassword(),userFromDB.getPassword())){
            return getAuthenticationResponse(null,null,"Incorrect password!",false);
        }
        if(passwordEncoder.matches(request.getPassword(),userFromDB.getPassword())){
            return getAuthenticationResponse(null,null,"You cannot use previous password!",false);
        }

        userFromDB.setPassword((passwordEncoder.encode(request.getPassword())));
        userFromDB.setLastModifiedDate(Timestamp.valueOf(now));


        User savedUser = null;
        var jwtToken = "";
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            savedUser = repository.save(userFromDB);
            jwtToken = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, jwtToken);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }

        return getAuthenticationResponse(savedUser,jwtToken,errorMessage,isSuccess);
    }

    public AuthenticationResponse deleteUser(RegisterRequest request, String id) {
        User userFromDB =  getUser(Long.valueOf(id));

        if(!userFromDB.getRealUsername().equals(request.getUsername())){
            return getAuthenticationResponse(null,null,"Operation not allowed!",false);
        }
        deleteAllUserTokens(userFromDB);
        String  errorMessage = "";
        Boolean isSuccess  = true;
        try {
            repository.delete(userFromDB);
        } catch (RuntimeException  ex ) {
            errorMessage = ex.getMessage();
            isSuccess = false;
            System.out.println(errorMessage);
        }

        return getAuthenticationResponse(null,null,errorMessage,isSuccess);

    }

    public ArrayList<AuthenticationResponse> getAllUsersWithUserRole() {
        final ArrayList<User> all = (ArrayList<User>) repository.findAllByRole(Role.USER);
        ArrayList<AuthenticationResponse> results = new ArrayList<>();
        all.forEach(user -> results.add(getAuthenticationResponse(user,null,null,true)));
        return  results;
    }
}