package com.fitverse.auth.service;

import com.fitverse.auth.dto.AuthResponse;
import com.fitverse.auth.dto.LoginRequest;
import com.fitverse.auth.dto.RegisterRequest;
import com.fitverse.auth.dto.UserResponse;
import com.fitverse.auth.entity.UserAccount;
import com.fitverse.auth.entity.UserRole;
import com.fitverse.auth.exception.InvalidCredentialsException;
import com.fitverse.auth.exception.UserAlreadyExistsException;
import com.fitverse.auth.mapper.UserMapper;
import com.fitverse.auth.repository.UserAccountRepository;
import com.fitverse.shared.security.JwtTokenProvider;
import java.util.Locale;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserAccountRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserAccountRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse register(RegisterRequest request) {
        repository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException(request.getEmail());
        });

        UserRole role = UserRole.valueOf(request.getRole().toUpperCase(Locale.US));
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserAccount saved = repository.save(new UserAccount(request.getEmail(), encodedPassword, role));
        UserResponse response = mapper.toResponse(saved);
        String subject = saved.getId() != null ? saved.getId().toString() : saved.getEmail();
        String token = tokenProvider.generateToken(subject, saved.getEmail(),
                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + saved.getRole().name())));
        return new AuthResponse(response, token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        UserAccount account = repository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);
        UserResponse response = mapper.toResponse(account);
        String subject = account.getId() != null ? account.getId().toString() : account.getEmail();
        String token = tokenProvider.generateToken(subject, account.getEmail(),
                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole().name())));
        return new AuthResponse(response, token);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toResponse);
    }
}

