package lab6.client.memory;

import lab6.common.dto.CommandResponseDto;
import lab6.common.exceptions.AuthorizationException;

import java.io.Serializable;

public class LoginPassword {
    private static String login;
    private static String password;

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        LoginPassword.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LoginPassword.password = password;
    }

}
