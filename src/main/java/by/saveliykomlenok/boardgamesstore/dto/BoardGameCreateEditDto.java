package by.saveliykomlenok.boardgamesstore.dto;

import lombok.Data;

@Data
public class BoardGameCreateEditDto {
    private String name;
    private double price;
    private int numberOfPlayers;
    private int age;
    private int amount;
    private Long manufacturer;
    private Long boardGameType;
}
