package ctw.screenscoreapi.Share.aop;

import ctw.screenscoreapi.Auth.exception.UserNotAuthenticatedException;
import ctw.screenscoreapi.Users.infra.session.UserSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthAspect {
    private UserSession userSession;

    public AuthAspect(UserSession userSession) {
        this.userSession = userSession;
    }

    @Before("@annotation(ctw.screenscoreapi.Share.aop.ToAuthenticate)")
    public void toAuthenticate() {
        Long userId = userSession.getUserId();

        if(userId == null) {
            throw new UserNotAuthenticatedException();
        }
    }
}
