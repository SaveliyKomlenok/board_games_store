package by.saveliykomlenok.boardgamesstore.util.exception.boardgame;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class AmountOfBoardGameExceededException extends ExceptionManager {
    public AmountOfBoardGameExceededException(String message) {
        super(message);
    }
}
