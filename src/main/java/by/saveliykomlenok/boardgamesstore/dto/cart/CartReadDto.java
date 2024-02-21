package by.saveliykomlenok.boardgamesstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartReadDto {
    private List<CartBoardGameReadDto> cartBoardGame;
    private List<CartAccessoryReadDto> cartAccessory;
    private double totalPrice;
}
