package by.saveliykomlenok.boardgamesstore.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderBoardGameCreateEditDto {
    private int amount;
    private Long boardGame;
    private Long mainOrder;
}
