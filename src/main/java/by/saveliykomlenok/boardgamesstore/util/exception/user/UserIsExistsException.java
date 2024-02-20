package by.saveliykomlenok.boardgamesstore.util.exception.user;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class UserIsExistsException extends ExceptionManager {
    public UserIsExistsException(String message) {
        super(message);
    }
}
