package by.saveliykomlenok.boardgamesstore.util.exception.cart;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class CartBoardGameMissingException extends ExceptionManager {
    public CartBoardGameMissingException(String message) {
        super(message);
    }
}
