package ctw.screenscoreapi.auth;

import ctw.screenscoreapi.Module.Auth.dtos.LoginRequest;
import ctw.screenscoreapi.Module.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Module.Auth.exception.InvalidCredentialsException;
import ctw.screenscoreapi.Module.Auth.mapper.AuthMapper;
import ctw.screenscoreapi.Module.Auth.service.AuthService;
import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Users.application.exception.UserNotFoundByEmailException;
import ctw.screenscoreapi.Module.Users.application.service.UserService;
import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Module.Users.domain.enums.Role;
import ctw.screenscoreapi.Module.Users.infra.session.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    AuthMapper authMapper;

    @Mock
    UserService userService;

    @Mock
    UserSession userSession;

    @InjectMocks
    AuthService authService;

    @Test
    @DisplayName("Should register user and return its ID when request is valid")
    public void shouldRegisterUserSuccessfully() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Pedro", "pedro@email.com", "123456@Aa");
        CreateUserRequest createRequest = new CreateUserRequest(request.name(), request.email(), request.password(), Role.CLIENT);
        when(authMapper.toCreateUserRequest(request)).thenReturn(createRequest);
        when(userService.create(createRequest)).thenReturn(1L);

        // Act
        long userId = authService.register(request);

        // Asserts
        verify(authMapper).toCreateUserRequest(request);
        verify(userService).create(createRequest);
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should authenticate user and set session when credentials are valid")
    public void shouldLoginSuccessfully() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("pedro@gmail.com", "123456@Aa");
        UserEntity userEntity = new UserEntity(1L, loginRequest.password(), "Pedro", loginRequest.email(), Role.CLIENT);
        when(userService.getFullUserByEmail(loginRequest.email())).thenReturn(userEntity);

        // Act
        authService.login(loginRequest);

        // Asserts
        verify(userService).getFullUserByEmail(loginRequest.email());
        verify(userSession).setUserId(userEntity.getId());
        verify(userSession).setRole(userEntity.getRole());
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException when password is invalid")
    public void shouldThrowInvalidCredentialsExceptionWhenPasswordIsInvalid() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("pedro@gmail.com", "123456@Aa");
        UserEntity userEntity = new UserEntity(1L, "otherPassword", "Pedro", loginRequest.email(), Role.CLIENT);
        when(userService.getFullUserByEmail(loginRequest.email())).thenReturn(userEntity);

        // Assert + Act
        assertThrows(InvalidCredentialsException.class, () -> authService.login(loginRequest));
        verify(userService).getFullUserByEmail(loginRequest.email());
        verify(userSession, never()).setUserId(any());
        verify(userSession, never()).setRole(any());
    }
}
