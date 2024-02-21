package by.saveliykomlenok.boardgamesstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBoardGameCreateEditDto {
    private int amount;
    private Long boardGame;
}
