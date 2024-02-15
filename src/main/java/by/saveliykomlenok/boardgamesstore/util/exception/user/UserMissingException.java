package by.saveliykomlenok.boardgamesstore.util.exception.user;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class UserMissingException extends ExceptionManager {
    public UserMissingException(String message) {
        super(message);
    }
}
