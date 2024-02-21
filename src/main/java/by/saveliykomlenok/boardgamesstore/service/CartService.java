package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartReadDto;
import by.saveliykomlenok.boardgamesstore.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartAccessoryService cartAccessoryService;
    private final CartBoardGameService cartBoardGameService;
    private final ModelMapper mapper;

    public CartReadDto listOfCarts(User user) {
        return CartReadDto.builder()
                .cartBoardGame(cartBoardGameService.findAll(user))
                .cartAccessory(cartAccessoryService.findAll(user))
                .totalPrice(getTotalBoardGamesPrice(user) + getTotalAccessoriesPrice(user))
                .build();
    }

    private double getTotalAccessoriesPrice(User user) {
        return cartAccessoryService.findAll(user).stream()
                .map(cartAccessoryReadDto -> mapper.map(cartAccessoryReadDto, CartAccessories.class))
                .map(cartAccessories -> cartAccessories.getAmount() * cartAccessories.getAccessory().getPrice())
                .mapToDouble(Double::doubleValue).sum();
    }

    private double getTotalBoardGamesPrice(User user) {
        return cartBoardGameService.findAll(user).stream()
                .map(cartBoardGameReadDto -> mapper.map(cartBoardGameReadDto, CartBoardGames.class))
                .map(cartBoardGames -> cartBoardGames.getAmount() * cartBoardGames.getBoardGame().getPrice())
                .mapToDouble(Double::doubleValue).sum();
    }
}
