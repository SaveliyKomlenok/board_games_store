package by.saveliykomlenok.boardgamesstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAccessoryCreateEditDto {
    private int amount;
    private Long accessory;
    private Long mainOrder;
}
