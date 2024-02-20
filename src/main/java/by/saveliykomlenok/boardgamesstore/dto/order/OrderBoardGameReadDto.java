package by.saveliykomlenok.boardgamesstore.dto.order;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBoardGameReadDto {
    private Long id;
    private int amount;
    private BoardGameReadDto boardGame;
    private MainOrderReadDto mainOrder;
}
