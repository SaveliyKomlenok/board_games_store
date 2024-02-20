package by.saveliykomlenok.boardgamesstore.dto.cart;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBoardGameReadDto {
    private Long id;
    private int amount;
    private BoardGameReadDto boardGame;
}
