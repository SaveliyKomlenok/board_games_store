package by.saveliykomlenok.boardgamesstore.dto;

import lombok.Data;

@Data
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