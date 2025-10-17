package com.fitverse.auth.service;

import com.fitverse.auth.dto.LoginRequest;
import com.fitverse.auth.dto.RegisterRequest;
import com.fitverse.auth.dto.UserResponse;
import com.fitverse.auth.entity.UserAccount;
import com.fitverse.auth.entity.UserRole;
import com.fitverse.auth.exception.InvalidCredentialsException;
import com.fitverse.auth.exception.UserAlreadyExistsException;
import com.fitverse.auth.mapper.UserMapper;
import com.fitverse.auth.repository.UserAccountRepository;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserAccountRepository repository;
    private final UserMapper mapper;

    public AuthService(UserAccountRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserResponse register(RegisterRequest request) {
        repository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException(request.getEmail());
        });

        UserRole role = UserRole.valueOf(request.getRole().toUpperCase(Locale.US));
        UserAccount saved = repository.save(new UserAccount(request.getEmail(), request.getPassword(), role));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest request) {
        UserAccount account = repository.findByEmail(request.getEmail())
                .filter(user -> user.getPasswordHash().equals(request.getPassword()))
                .orElseThrow(InvalidCredentialsException::new);
        return mapper.toResponse(account);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        UserAccount account = repository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException());
        return mapper.toResponse(account);
    }
}
