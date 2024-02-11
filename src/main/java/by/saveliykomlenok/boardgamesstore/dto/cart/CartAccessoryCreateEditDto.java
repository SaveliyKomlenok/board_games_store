package by.saveliykomlenok.boardgamesstore.dto.cart;

import lombok.Data;

@Data
public class CartAccessoryCreateEditDto {
    private int amount;
    private Long accessory;
    private Long user;
}
