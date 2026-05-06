package ctw.screenscoreapi.Auth.mapper;

import ctw.screenscoreapi.Module.Auth.dtos.RegisterRequest;
import ctw.screenscoreapi.Module.Auth.mapper.AuthMapper;
import ctw.screenscoreapi.Module.Users.application.dtos.create.CreateUserRequest;
import ctw.screenscoreapi.Module.Users.domain.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AuthMapperTest {
    AuthMapper authMapper = new AuthMapper();

    @Test
    @DisplayName("Should map RegisterRequest to CreateUserRequest correctly")
    public void shouldMapRegisterRequestToCreateUserRequestCorrectly() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Pedro", "pedro@gmail.com", "123456@Aa");

        // Act
        CreateUserRequest user = authMapper.toCreateUserRequest(request);

        // Assert
        assertThat(user.name()).isEqualTo(request.name());
        assertThat(user.email()).isEqualTo(request.email());
        assertThat(user.password()).isEqualTo(request.password());
        assertThat(user.role()).isEqualTo(Role.CLIENT);
    }
}
