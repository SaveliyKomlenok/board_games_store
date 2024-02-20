package by.saveliykomlenok.boardgamesstore.dto.accessory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryCreateEditDto {
    private String name;
    private double price;
    private int amount;
    private Long accessoryType;
    private Long manufacturer;
}
