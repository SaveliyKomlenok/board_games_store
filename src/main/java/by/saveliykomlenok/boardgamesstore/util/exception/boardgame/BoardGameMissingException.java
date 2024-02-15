package by.saveliykomlenok.boardgamesstore.util.exception.boardgame;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class BoardGameMissingException extends ExceptionManager {
    public BoardGameMissingException(String message) {
        super(message);
    }
}
