package ctw.screenscoreapi.auth;

import ctw.screenscoreapi.Module.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Module.Auth.mapper.AuthMapper;
import ctw.screenscoreapi.Module.Auth.service.AuthService;
import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Users.application.service.UserService;
import ctw.screenscoreapi.Module.Users.domain.enums.Role;
import ctw.screenscoreapi.Module.Users.infra.session.UserSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        CreateUserRequest createRequest = new CreateUserRequest(request.name(), request.email(), request.password(), Role.CLIENTE);
        when(authMapper.toCreateUserRequest(request)).thenReturn(createRequest);
        when(userService.create(createRequest)).thenReturn(1L);

        // Act
        long userId = authService.register(request);

        // Asserts
        verify(authMapper).toCreateUserRequest(request);
        verify(userService).create(createRequest);
        assertThat(userId).isEqualTo(1L);
    }
}
