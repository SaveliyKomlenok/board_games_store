package by.saveliykomlenok.boardgamesstore.dto.boardgame;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardGameCreateEditDto {
    private String name;
    private double price;
    private int numberOfPlayers;
    private int age;
    private int amount;
    private Long manufacturer;
    private Long boardGameType;
}
