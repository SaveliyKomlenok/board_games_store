package by.saveliykomlenok.boardgamesstore.dto.cart;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import lombok.Data;

@Data
public class CartAccessoryReadDto {
    private Long id;
    private int amount;
    private AccessoryReadDto accessory;
}
