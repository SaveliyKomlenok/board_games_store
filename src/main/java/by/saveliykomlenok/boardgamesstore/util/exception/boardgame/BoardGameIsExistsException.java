package by.saveliykomlenok.boardgamesstore.util.exception.boardgame;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class BoardGameIsExistsException extends ExceptionManager {
    public BoardGameIsExistsException(String message) {
        super(message);
    }
}
