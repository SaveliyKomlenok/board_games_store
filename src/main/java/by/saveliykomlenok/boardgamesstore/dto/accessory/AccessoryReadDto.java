package by.saveliykomlenok.boardgamesstore.dto.accessory;

import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryReadDto {
    private Long id;
    private String name;
    private double price;
    private int amount;
    private AccessoryTypeReadDto accessoryType;
    private ManufacturerReadDto manufacturer;
}
