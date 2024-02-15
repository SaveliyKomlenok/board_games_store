package by.saveliykomlenok.boardgamesstore.util.exception.order;

import by.saveliykomlenok.boardgamesstore.util.exception.ExceptionManager;

public class OrderMissingException extends ExceptionManager {
    public OrderMissingException(String message) {
        super(message);
    }
}
