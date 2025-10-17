package com.fitverse.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.auth.dto.LoginRequest;
import com.fitverse.auth.dto.RegisterRequest;
import com.fitverse.auth.dto.UserResponse;
import com.fitverse.auth.entity.UserAccount;
import com.fitverse.auth.entity.UserRole;
import com.fitverse.auth.exception.InvalidCredentialsException;
import com.fitverse.auth.exception.UserAlreadyExistsException;
import com.fitverse.auth.mapper.UserMapper;
import com.fitverse.auth.repository.UserAccountRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserAccountRepository repository;

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService(repository, mapper);
    }

    @Test
    void registerCreatesNewUser() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("user@fitverse.dev");
        request.setPassword("Passw0rd!");
        request.setRole("USER");

        when(repository.findByEmail("user@fitverse.dev")).thenReturn(Optional.empty());
        when(repository.save(any(UserAccount.class)))
                .thenAnswer(invocation -> {
                    UserAccount account = invocation.getArgument(0);
                    account.setCreatedAt(Instant.now());
                    return account;
                });

        UserResponse response = authService.register(request);

        verify(repository).save(any(UserAccount.class));
        assertThat(response.getEmail()).isEqualTo("user@fitverse.dev");
        assertThat(response.getRole()).isEqualTo("USER");
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

        when(repository.findByEmail("user@fitverse.dev")).thenReturn(Optional.of(new UserAccount("user@fitverse.dev", "Passw0rd!", UserRole.USER)));

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
    }
}
