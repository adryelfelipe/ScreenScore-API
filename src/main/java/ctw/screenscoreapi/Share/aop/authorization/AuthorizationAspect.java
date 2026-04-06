package ctw.screenscoreapi.Share.aop.authorization;

import ctw.screenscoreapi.Module.Auth.exception.UserNotAuthorizedException;
import ctw.screenscoreapi.Module.Users.domain.entity.UserEntity;
import ctw.screenscoreapi.Module.Users.domain.enums.Role;
import ctw.screenscoreapi.Module.Users.domain.repository.UserRepository;
import ctw.screenscoreapi.Module.Users.infra.session.UserSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class AuthorizationAspect {
    private UserRepository userRepository;
    private UserSession userSession;

    public AuthorizationAspect(UserRepository userRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    @Before("@annotation(ctw.screenscoreapi.Share.aop.authorization.ToAuthorize)")
    public void toAuthorize() {
        Long userId = userSession.getUserId();

        UserEntity user = userRepository.findById(userId).get();

        if(user.getRole() != Role.ADMIN) {
            throw new UserNotAuthorizedException();
        }
    }
}
