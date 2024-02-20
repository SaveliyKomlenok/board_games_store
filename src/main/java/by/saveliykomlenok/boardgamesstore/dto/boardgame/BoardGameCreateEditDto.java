package by.saveliykomlenok.boardgamesstore.dto.boardgame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameCreateEditDto {
    private String name;
    private double price;
    private int numberOfPlayers;
    private int age;
    private int amount;
    private Long manufacturer;
    private Long boardGameType;
}
