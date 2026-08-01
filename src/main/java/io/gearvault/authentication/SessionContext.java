package io.gearvault.authentication;

import io.gearvault.model.User;
import org.springframework.stereotype.Component;

@Component
public class SessionContext {

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
