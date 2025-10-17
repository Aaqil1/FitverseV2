package com.fitverse.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.auth.dto.AuthResponse;
import com.fitverse.auth.dto.LoginRequest;
import com.fitverse.auth.dto.RegisterRequest;
import com.fitverse.auth.entity.UserAccount;
import com.fitverse.auth.entity.UserRole;
import com.fitverse.auth.exception.InvalidCredentialsException;
import com.fitverse.auth.exception.UserAlreadyExistsException;
import com.fitverse.auth.mapper.UserMapper;
import com.fitverse.auth.repository.UserAccountRepository;
import com.fitverse.shared.security.JwtTokenProvider;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccountRepository repository;

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService(repository, mapper, passwordEncoder, tokenProvider);
    }

    @Test
    void registerCreatesNewUser() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("user@fitverse.dev");
        request.setPassword("Passw0rd!");
        request.setRole("USER");

        when(repository.findByEmail("user@fitverse.dev")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Passw0rd!")).thenReturn("encoded");
        when(tokenProvider.generateToken(any(), any(), any())).thenReturn("token");
        when(repository.save(any(UserAccount.class)))
                .thenAnswer(invocation -> {
                    UserAccount account = invocation.getArgument(0);
                    account.setCreatedAt(Instant.now());
                    return account;
                });

        AuthResponse response = authService.register(request);

        verify(repository).save(any(UserAccount.class));
        assertThat(response.getUser().getEmail()).isEqualTo("user@fitverse.dev");
        assertThat(response.getUser().getRole()).isEqualTo("USER");
        assertThat(response.getAccessToken()).isEqualTo("token");
    }

    @Test
    void registerThrowsWhenUserExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("user@fitverse.dev");
        request.setPassword("Passw0rd!");
        request.setRole("USER");

        when(repository.findByEmail("user@fitverse.dev")).thenReturn(Optional.of(new UserAccount("user@fitverse.dev", "pwd", UserRole.USER)));

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    void loginThrowsForInvalidCredentials() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@fitverse.dev");
        request.setPassword("wrong");

        UserAccount stored = new UserAccount("user@fitverse.dev", "hash", UserRole.USER);
        when(repository.findByEmail("user@fitverse.dev")).thenReturn(Optional.of(stored));
        when(passwordEncoder.matches("wrong", "hash")).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
    }
}
