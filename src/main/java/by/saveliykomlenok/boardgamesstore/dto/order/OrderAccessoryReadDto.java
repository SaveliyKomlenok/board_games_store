package by.saveliykomlenok.boardgamesstore.dto.order;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import lombok.Data;

@Data
public class OrderAccessoryReadDto {
    private Long id;
    private int amount;
    private AccessoryReadDto accessory;
    private MainOrderReadDto mainOrder;
}
