package by.saveliykomlenok.boardgamesstore.dto.order;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import lombok.Data;

@Data
public class OrderBoardGameReadDto {
    private Long id;
    private int amount;
    private BoardGameReadDto boardGame;
    private MainOrderReadDto mainOrder;
}
