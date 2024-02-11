package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.*;
import by.saveliykomlenok.boardgamesstore.repositories.CartAccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartAccessoryService {
    private final CartAccessoryRepository cartAccessoryRepository;
    private final AccessoryService accessoryService;
    private final UserService userService;
    private final ModelMapper mapper;

    public List<CartAccessoryReadDto> findAll(User user) {
        return cartAccessoryRepository.findAll().stream()
                .map(cartAccessories -> mapper.map(cartAccessories, CartAccessoryReadDto.class))
                .toList();
//        return cartAccessoryRepository.findCartAccessoriesByUserId(user.getId()).stream()
//                .map(cartAccessories -> mapper.map(cartAccessories, CartAccessoryReadDto.class))
//                .toList();
    }

    public CartAccessoryReadDto findById(Long id) {
        return cartAccessoryRepository.findById(id)
                .map(cartAccessories -> mapper.map(cartAccessories, CartAccessoryReadDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public CartAccessoryReadDto create(User user, CartAccessoryCreateEditDto cartAccessoryDto) {
        CartAccessories cartAccessories = Optional.of(cartAccessoryDto)
                .map(cartAccessoryCreateEditDto -> mapper.map(cartAccessoryDto, CartAccessories.class))
                .orElseThrow();
        if (cartAccessoryRepository.existsCartAccessoriesByAccessoryIdAndUserId(cartAccessoryDto.getAccessory(), cartAccessoryDto.getUser())) {
            cartAccessories = cartAccessoryRepository.findCartAccessoriesByAccessoryIdAndUserId(cartAccessoryDto.getAccessory(), cartAccessoryDto.getUser());
            cartAccessories.setAmount(cartAccessories.getAmount() + cartAccessoryDto.getAmount());
        }
        cartAccessories.setAccessory(mapper.map(accessoryService.findById(cartAccessoryDto.getAccessory()), Accessory.class));
        cartAccessories.setUser(mapper.map(userService.findById(cartAccessoryDto.getUser()), User.class));
        //cartBoardGames.setUser(user); Реализовать после JWT

        cartAccessoryRepository.saveAndFlush(cartAccessories);
        return mapper.map(cartAccessories, CartAccessoryReadDto.class);
    }

    @Transactional
    public CartAccessoryReadDto update(Long id, CartAccessoryCreateEditDto cartAccessoryDto) {
        CartAccessories cartAccessories = cartAccessoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        mapper.map(cartAccessoryDto, cartAccessories);

        cartAccessories.setAccessory(mapper.map(accessoryService.findById(cartAccessoryDto.getAccessory()), Accessory.class));
        cartAccessories.setUser(mapper.map(userService.findById(cartAccessoryDto.getUser()), User.class));

        cartAccessoryRepository.saveAndFlush(cartAccessories);

        return mapper.map(cartAccessories, CartAccessoryReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        cartAccessoryRepository.findById(id)
                .map(cartAccessories -> {
                    cartAccessoryRepository.delete(cartAccessories);
                    cartAccessoryRepository.flush();
                    return true;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
