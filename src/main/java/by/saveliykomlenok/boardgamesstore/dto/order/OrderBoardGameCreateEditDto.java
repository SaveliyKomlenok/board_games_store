package by.saveliykomlenok.boardgamesstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBoardGameCreateEditDto {
    private int amount;
    private Long boardGame;
    private Long mainOrder;
}
