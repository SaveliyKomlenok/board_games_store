package by.saveliykomlenok.boardgamesstore.dto.cart;

import lombok.Data;

@Data
public class CartBoardGameCreateEditDto {
    private int amount;
    private Long boardGame;
    private Long user;
}
