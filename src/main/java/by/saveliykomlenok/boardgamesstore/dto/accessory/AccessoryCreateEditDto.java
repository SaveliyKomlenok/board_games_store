package by.saveliykomlenok.boardgamesstore.dto.accessory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessoryCreateEditDto {
    private String name;
    private double price;
    private int amount;
    private Long accessoryType;
    private Long manufacturer;
}
