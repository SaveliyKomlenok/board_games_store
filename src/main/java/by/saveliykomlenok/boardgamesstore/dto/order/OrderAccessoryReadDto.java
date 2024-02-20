package by.saveliykomlenok.boardgamesstore.dto.order;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAccessoryReadDto {
    private Long id;
    private int amount;
    private AccessoryReadDto accessory;
    private MainOrderReadDto mainOrder;
}
