package by.saveliykomlenok.boardgamesstore.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderAccessoryCreateEditDto {
    private int amount;
    private Long accessory;
    private Long mainOrder;
}
