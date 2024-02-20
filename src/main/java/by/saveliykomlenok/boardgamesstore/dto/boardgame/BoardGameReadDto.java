package by.saveliykomlenok.boardgamesstore.dto.boardgame;

import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameReadDto {
    private Long id;
    private String name;
    private double price;
    private int numberOfPlayers;
    private int age;
    private int amount;
    private ManufacturerReadDto manufacturer;
    private BoardGameTypeReadDto boardGameType;
}