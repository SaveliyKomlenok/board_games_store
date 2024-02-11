package by.saveliykomlenok.boardgamesstore.dto.cart;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import lombok.Data;

@Data
public class CartBoardGameReadDto {
    private Long id;
    private int amount;
    private BoardGameReadDto boardGame;
}
