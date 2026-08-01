package io.gearvault.authentication;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean authenticate(String candidate, String encrypted) {
        return BCrypt.checkpw(candidate, encrypted);
    }
}
