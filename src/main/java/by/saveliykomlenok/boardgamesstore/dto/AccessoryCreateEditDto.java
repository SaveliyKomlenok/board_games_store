package by.saveliykomlenok.boardgamesstore.dto;

import lombok.Data;

@Data
public class AccessoryCreateEditDto {
    private String name;
    private double price;
    private int amount;
    private Long accessoryType;
    private Long manufacturer;
}
