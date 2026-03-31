package ctw.screenscoreapi.Share.aop.authentication;

import ctw.screenscoreapi.Auth.exception.UserNotAuthenticatedException;
import ctw.screenscoreapi.Users.infra.session.UserSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class AuthenticationAspect {
    private UserSession userSession;

    public AuthenticationAspect(UserSession userSession) {
        this.userSession = userSession;
    }

    @Before("@annotation(ctw.screenscoreapi.Share.aop.authentication.ToAuthenticate)")
    public void toAuthenticate() {
        Long userId = userSession.getUserId();

        if(userId == null) {
            throw new UserNotAuthenticatedException();
        }
    }
}
