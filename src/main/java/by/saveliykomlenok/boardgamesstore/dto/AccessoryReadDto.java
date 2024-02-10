package by.saveliykomlenok.boardgamesstore.dto;

import lombok.Data;

@Data
public class AccessoryReadDto {
    private Long id;
    private String name;
    private double price;
    private int amount;
    private AccessoryTypeReadDto accessoryType;
    private ManufacturerReadDto manufacturer;
}
