package by.saveliykomlenok.boardgamesstore.dto.cart;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAccessoryReadDto {
    private Long id;
    private int amount;
    private AccessoryReadDto accessory;
}
